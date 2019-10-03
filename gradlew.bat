<?xml version="1.0" encoding="utf-8"?>
<!--
     Copyright (C) 2017 The Android Open Source Project

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.
-->

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
     android:id="@+id/topPanel"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:orientation="vertical"
     android:gravity="top|center_horizontal">

    <!-- If the client uses a customTitle, it will be added here. -->

    <LinearLayout
        android:id="@+id/title_template"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center_horizontal"
        android:layout_marginTop="24dp"
        android:orientation="vertical">

        <ImageView
            android:id="@android:id/icon"
            android:adjustViewBounds="true"
            android:maxHeight="24dp"
            android:maxWidth="24dp"
            android:layout_gravity="center_horizontal"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

        <androidx.appcompat.widget.DialogTitle
            android:id="@+id/alertTitle"
            style="?android:attr/windowTitleStyle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center" />

    </LinearLayout>

    <android.widget.Space
        android:id="@+id/titleDividerNoCustom"
        android:layout_width="match_parent"
        android:layout_height="@dimen/abc_dialog_title_divider_material"
        android:visibility="gone"/>
</LinearLayout>
                                                                                               