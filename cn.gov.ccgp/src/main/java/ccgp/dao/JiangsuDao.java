package ccgp.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import ccgp.main.Beijing;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import ccgp.domain.Ccgp;
import ccgp.utils.JDBCUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JiangsuDao {
	public Logger logger = LogManager.getLogger(JiangsuDao.class.getName());

	QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());

public Date getMaxTime(String status) {
		
		String sql = "SELECT MAX(TIME) FROM jiangsu WHERE STATUS=?";
		Object[] params = {status};
		Date date=null;
		try {
			date = qr.query(sql, new ScalarHandler<Date>(), params );
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return date;
	}
	public void save(ArrayList<Ccgp> list) {
		if (list!=null&&list.size()>0) {
			for (Ccgp ccgp : list) {
				Object[] params= {
						ccgp.getTitle(),
						ccgp.getTime(),
						ccgp.getArea(),
						ccgp.getHref(),
						ccgp.getStatus(),
				};
				String sql="INSERT INTO jiangsu (title,TIME,AREA,href,STATUS) VALUES (?,?,?,?,?)";
				try {
					qr.insert(sql, new ScalarHandler<Object>(), params);
				} catch (SQLException e) {
					if (e.getErrorCode() == '1'){
						logger.warn("�Ѵ��ڼ�¼,���ӣ�"+ccgp.getHref());
					}else {
						logger.error(e.getMessage());
					}

				}
			}
		}
	}
}
