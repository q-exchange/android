package top.biduo.exchange.app;

import android.content.Context;

import top.biduo.exchange.data.DataRepository;
import top.biduo.exchange.data.LocalDataSource;
import top.biduo.exchange.data.RemoteDataSource;


/**
 * Created by Administrator on 2017/9/25.
 */

public class Injection {
    public static DataRepository provideTasksRepository(Context context) {
        return DataRepository.getInstance(RemoteDataSource.getInstance(), LocalDataSource.getInstance(context));
    }
}
