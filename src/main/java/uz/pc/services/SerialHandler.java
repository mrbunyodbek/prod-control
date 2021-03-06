package uz.pc.services;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import jssc.SerialPortList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.pc.db.dao.AttendanceDAOImpl;

@Service
public class SerialHandler extends Thread {

    private AttendanceDAOImpl dao;
    private static boolean checkupOne;
    private static boolean checkupTwo;

    private static Logger logger = LoggerFactory.getLogger(SerialHandler.class);

    public SerialHandler(AttendanceDAOImpl dao) {
        this.dao = dao;
        checkupOne = false;
        checkupTwo = false;
    }

    @Override
    public void run() {
        SerialParameters sp = new SerialParameters();
        Modbus.setLogLevel(Modbus.LogLevel.LEVEL_RELEASE);
        try {
            // you can use just string to get connection with remote slave,
            // but you can also get a list of all serial ports available at your system.
            String[] dev_list = SerialPortList.getPortNames();
            // if there is at least one serial port at your system
            if (dev_list.length > 0) {
                logger.info("Device is connected.");
                // you can choose the one of those you need
                sp.setDevice(dev_list[0]);
                // these parameters are set by default
                sp.setBaudRate(SerialPort.BaudRate.BAUD_RATE_57600);
                sp.setDataBits(8);
                sp.setParity(SerialPort.Parity.NONE);
                sp.setStopBits(1);

                int slaveId = 1;

                for(;;) {
                    ModbusMaster m = ModbusMasterFactory.createModbusMasterRTU(sp);
                    try {
                        m.connect();
                    } catch (ModbusIOException e1) {
                        logger.warn("Device not found. Will try to reconnect after 10 seconds.");
                        logger.warn("Please be sure that device is connected properly, otherwise attendances won't be registered by application.");
                        Thread.sleep(10000);
                        this.run();
                    }

                    if (slaveId == 1 && !checkupOne) {
                        setRegistersToNull(m, slaveId);
                        checkupOne = true;
                        logger.info("Registers for Slave 1 has been set to NULL");
                    } else if (slaveId == 2 && !checkupTwo) {
                        setRegistersToNull(m, slaveId);
                        checkupTwo = true;
                        logger.info("Registers for Slave 2 has been set to NULL");
                    }

                    //you can invoke #connect method manually, otherwise it'll be invoked automatically
                    try {
                        int startingPoint = 0;
                        int registersQuantity = 2;

                        StringBuilder cardId = new StringBuilder();

                        int[] registerValues = m.readHoldingRegisters(slaveId, startingPoint, registersQuantity);

                        cardId.append(registerValues[0]);

                        String data = Integer.toString(registerValues[1]);
                        if (data.length() == 3) data = "0" + data;
                        cardId.append(data);

                        saveAttendanceAccordingToSlaveId(m, slaveId, cardId);

                        if (slaveId == 1) slaveId = 2;
                        else slaveId = 1;
                        Thread.sleep(200);

                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            m.disconnect();
                        } catch (ModbusIOException e1) {
                            e1.printStackTrace();
                            logger.warn("Device not found. Will try to reconnect after 10 seconds.");
                            logger.warn("Please be sure that device is connected properly, otherwise attendances won't be registered by application.");
                            Thread.sleep(10000);
                            this.run();
                        }
                    }
                }
            } else {
                logger.warn("Device not found. Will try to reconnect after 10 seconds.");
                logger.warn("Please be sure that device is connected properly, otherwise attendances won't be registered by application.");
                Thread.sleep(10000);
                this.run();
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Sets first two registers of the given slave (slaveId) to null
     */
    private void setRegistersToNull(ModbusMaster m, int slaveId) {
        try {

            m.writeSingleRegister(slaveId, 0, 0);
            m.writeSingleRegister(slaveId, 1, 0);

        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            e.printStackTrace();
        }
    }
    private void setResult(ModbusMaster m, int slaveId, boolean result) {
        try {
            if (result) m.writeSingleRegister(slaveId, 3, 1);
            else m.writeSingleRegister(slaveId, 4, 1);

        } catch (ModbusProtocolException | ModbusNumberException | ModbusIOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves attendance according to an ID of the slave.
     *
     * @param slaveId Current slave ID.
     * @param cardId Retrieved card ID from slave's registers.
     */
    private void saveAttendanceAccordingToSlaveId(ModbusMaster m, int slaveId, StringBuilder cardId) {
        if (slaveId == 1) {
            if (!cardId.toString().equals("00")) {
                boolean result = dao.registerEmployeeArrival(cardId.toString());
                setResult(m, slaveId, result);
            }
        } else if(slaveId == 2) {
            if (!cardId.toString().equals("00")) {
                boolean result = dao.registerEmployeeDeparture(cardId.toString());
                setResult(m, slaveId, result);
            }
        }

        setRegistersToNull(m, slaveId);

    }
}
