# BottomBarLayout 底部导航组件
### 依赖Library

从远程依赖:

在根目录的settings.gradle中加入

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url "https://jitpack.io" }
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
        maven {
            url 'https://maven.aliyun.com/repository/public'
        }
        maven {
        		credentials {
                username '606eaa398e4139b4d75018a8'
                password 'm3DOt91WxhP_'
            }
            url 'https://packages.aliyun.com/maven/repository/2092914-release-ZCzD1E/'
        }
}
```

在主项目app的build.gradle中依赖

```
dependencies {
	....
    implementation 'com.lnkj.libs.LNAndroidLibs:BottomBarLayout:0.0.4.2'
}
```

### 使用
```
<com.mufeng.bottombarlayout.BottomBarLayout
        android:id="@+id/bbl"
        android:layout_width="match_parent"
        android:layout_height="49dp"
        android:elevation="5dp"
        android:layout_gravity="center"
        android:background="@color/white"
        android:gravity="center"
        android:orientation="horizontal">

        <com.mufeng.bottombarlayout.BottomBarItem
            android:id="@+id/bblHome"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            app:iconHeight="24dp"
            app:iconNormal="@mipmap/tabbar_icon_home_n"
            app:iconSelected="@mipmap/tabbar_icon_home_s"
            app:iconWidth="24dp"
            app:itemMarginTop="2dp"
            app:itemText="首页"
            app:itemTextSize="10sp"
            app:textColorNormal="@color/tab_normal_color"
            app:textColorSelected="@color/tab_selected_color" />

        <com.mufeng.bottombarlayout.BottomBarItem
            android:id="@+id/bblChat"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            app:iconHeight="24dp"
            app:iconNormal="@mipmap/tabbar_icon_chat_n"
            app:iconSelected="@mipmap/tabbar_icon_chat_s"
            app:iconWidth="24dp"
            app:itemMarginTop="2dp"
            app:itemText="聊天"
            app:itemTextSize="10sp"
            app:textColorNormal="@color/tab_normal_color"
            app:textColorSelected="@color/tab_selected_color" />

        <com.mufeng.bottombarlayout.BottomBarItem
            android:id="@+id/bblOrganization"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            app:iconHeight="24dp"
            app:iconNormal="@mipmap/tabbar_icon_group_n"
            app:iconSelected="@mipmap/tabbar_icon_group_s"
            app:iconWidth="24dp"
            app:itemMarginTop="2dp"
            app:itemText="组织"
            app:itemTextSize="10sp"
            app:textColorNormal="@color/tab_normal_color"
            app:textColorSelected="@color/tab_selected_color" />

        <com.mufeng.bottombarlayout.BottomBarItem
            android:id="@+id/bblMessage"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            app:iconHeight="24dp"
            app:iconNormal="@mipmap/tabbar_icon_message_n"
            app:iconSelected="@mipmap/tabbar_icon_message_s"
            app:iconWidth="24dp"
            app:itemMarginTop="2dp"
            app:itemText="消息"
            app:itemTextSize="10sp"
            app:textColorNormal="@color/tab_normal_color"
            app:textColorSelected="@color/tab_selected_color" />

        <com.mufeng.bottombarlayout.BottomBarItem
            android:id="@+id/bblMine"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="1"
            app:iconHeight="24dp"
            app:iconNormal="@mipmap/tabbar_icon_me_n"
            app:iconSelected="@mipmap/tabbar_icon_me_s"
            app:iconWidth="24dp"
            app:itemMarginTop="2dp"
            app:itemText="我的"
            app:itemTextSize="10sp"
            app:textColorNormal="@color/tab_normal_color"
            app:textColorSelected="@color/tab_selected_color" />

    </com.mufeng.bottombarlayout.BottomBarLayout>
```

Kotlin代码中的使用
```
// 关联ViewPager2
val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, fragments)
binding.viewPager.adapter = adapter
binding.viewPager.isUserInputEnabled = false
binding.viewPager.isSaveEnabled = false
binding.bbl.setViewPager(binding.viewPager)

// 常用方法
// 设置当前选中
binding.bbl.setCurrentItem(currentItem)
// 设置消息未读数
binding.bbl.setUnread(position,unreadNum)
// 设置提示消息
binding.bbl.setMsg(position,msg)
// 隐藏提示消息
binding.bbl.hideMsg(position)
// 显示提示的小红点
binding.bbl.showNotify(position)
// 隐藏提示的小红点
binding.bbl.hideNotify(position)

// 设置点击事件
// bottomBarItem: 点击的Item
// previousPosition: 点击之前的索引
// currentPosition: 当前点击的索引
binding.bbl.setOnItemSelectedListener { bottomBarItem, previousPosition, currentPosition -> 
    
}

// 如果要拦截点击的Item
binding.bblHome.clickWithTrigger { 
    if(AccountUtils.isLogin()){
        binding.bbl.currentItem = 1
    }else{
        // 去登录
    }
}
```