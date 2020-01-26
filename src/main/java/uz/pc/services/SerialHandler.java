package uz.pc.services;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.serial.SerialParameters;
import com.intelligt.modbus.jlibmodbus.serial.SerialPort;
import com.intelligt.modbus.jlibmodbus.serial.SerialPortException;
import jssc.SerialPortList;
import org.springframework.stereotype.Service;
import uz.pc.db.dao.AttendanceDAOImpl;

@Service
public class SerialHandler extends Thread {

    private AttendanceDAOImpl dao;
    private static boolean checkupOne;
    private static boolean checkupTwo;

    public SerialHandler(AttendanceDAOImpl dao) {
        this.dao = dao;
        checkupOne = false;
        checkupTwo = false;
    }

    @Override
    public void run() {
        SerialParameters sp = new SerialParameters();
        Modbus.setLogLevel(Modbus.LogLevel.LEVEL_DEBUG);
        try {
            // you can use just string to get connection with remote slave,
            // but you can also get a list of all serial ports available at your system.
            String[] dev_list = SerialPortList.getPortNames();
            // if there is at least one serial port at your system
            if (dev_list.length > 0) {
                // you can choose the one of those you need

//                System.out.println(dev_list.length);
                sp.setDevice(dev_list[0]);
                // these parameters are set by default
                sp.setBaudRate(SerialPort.BaudRate.BAUD_RATE_57600);
                sp.setDataBits(8);
                sp.setParity(SerialPort.Parity.NONE);
                sp.setStopBits(1);

                int slaveId = 1;

                for(;;) {
                    ModbusMaster m = ModbusMasterFactory.createModbusMasterRTU(sp);
                    m.connect();
                    if (slaveId == 1 && !checkupOne) {
                        setRegistersToNull(m, slaveId);
                        checkupOne = true;
                        System.out.println("REGISTERS FOR SLAVE 1 HAS BEEN SET TO NULL");
                    } else if (slaveId == 2 && !checkupTwo) {
                        setRegistersToNull(m, slaveId);
                        checkupTwo = true;
                        System.out.println("REGISTERS FOR SLAVE 2 HAS BEEN SET TO NULL");
                    }

                    //you can invoke #connect method manually, otherwise it'll be invoked automatically
                    try {
                        int startingPoint = 0;
                        int registersQuantity = 2;

                        StringBuilder cardId = new StringBuilder();

                        int[] registerValues = m.readHoldingRegisters(slaveId, startingPoint, registersQuantity);

                        // print values
                        for (int value : registerValues) {
                            cardId.append(value);
                        }

//                        setRegistersToNull(m, slaveId);

                        saveAttendanceAccordingToSlaveId(m, slaveId, cardId);

                        if (slaveId == 1) slaveId = 2;
                        else slaveId = 1;

                        System.out.println("SLAVE_ID: " + slaveId);
                        Thread.sleep(200);

//                        if (m.isConnected()) {
//                            break;
//                        }

                    } catch (RuntimeException e) {
                        throw e;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            m.disconnect();
                        } catch (ModbusIOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
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

    /**
     * Saves attendance according to an ID of the slave.
     *
     * @param slaveId Current slave ID.
     * @param cardId Retrieved card ID from slave's registers.
     */
    private void saveAttendanceAccordingToSlaveId(ModbusMaster m, int slaveId, StringBuilder cardId) {
        if (slaveId == 1) {
            if (!cardId.toString().equals("00")) {
                dao.registerEmployeeArrival(cardId.toString());
            }
        } else if(slaveId == 2) {
            if (!cardId.toString().equals("00")) {
                dao.registerEmployeeDeparture(cardId.toString());
            }
        }

        setRegistersToNull(m, slaveId);

    }
}
