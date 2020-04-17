package uz.pc.db.dao.interfaces;

import uz.pc.db.dto.StatisticsDTO;

public interface DashboardDAO {

    StatisticsDTO collectStatsForProduction(String month);

}
