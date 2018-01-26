package com.wp.common.dagger;

import com.wp.common.MainActivity;

import dagger.Component;

/**
 * Created by WangPing on 2018/1/23.
 */
@Component
public interface ActivityComponent {
    void inject(MainActivity activity);
}
