package me.byungjin.gps.listeners;

import android.view.View;

import me.byungjin.gps.Manager;

public class SetDestinationListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Manager.setURL();
    }
}
