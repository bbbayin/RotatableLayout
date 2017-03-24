# RotatableLayout
点击可以翻转的layout
# PreView
![screenshoot](https://github.com/ShaqCc/RotatableLayout/blob/master/RotatableProject/screenshoot/preview.gif)
# Usage
## Step1

compile 'com.shaqcc.rotatablecard:rotatablecardlib:1.0.0'

## Step2
在xml文件中直接引用：
```
    <com.shaqcc.rotatablecard.view.RotatableLayout
        android:layout_width="match_parent"
        android:layout_height="90dp">
        <!--behind view-->
        <include layout="@layout/include_profile_behind_layout"/>
        <!--front view-->
        <include layout="@layout/include_profile_front_layout"/>
    </com.shaqcc.rotatablecard.view.RotatableLayout>
```
