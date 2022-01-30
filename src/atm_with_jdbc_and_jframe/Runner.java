package atm_with_jdbc_and_jframe;

import atm_with_jdbc_and_jframe.db.DataSource;
import atm_with_jdbc_and_jframe.ui.AtmFrame;

public class Runner {
    public static void main(String [] args){
        DataSource dataSource=new DataSource();
        dataSource.createDatabaseIfNotExists();
        dataSource=dataSource.createTables();
        AtmFrame frame=new AtmFrame(dataSource);
    }
}
