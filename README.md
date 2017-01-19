# RotatableLayout
点击可以翻转的layout
# Usage
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
