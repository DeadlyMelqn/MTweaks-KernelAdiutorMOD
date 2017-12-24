/*
 * Copyright (C) 2015-2016 Willi Ye <williye97@gmail.com>
 *
 * This file is part of Kernel Adiutor.
 *
 * Kernel Adiutor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kernel Adiutor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kernel Adiutor.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.moro.mtweaks.fragments.tools;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.moro.mtweaks.R;
import com.moro.mtweaks.activities.DataSharingSearchActivity;
import com.moro.mtweaks.fragments.DescriptionFragment;
import com.moro.mtweaks.fragments.RecyclerViewFragment;
import com.moro.mtweaks.services.monitor.Monitor;
import com.moro.mtweaks.utils.Prefs;
import com.moro.mtweaks.utils.Utils;
import com.moro.mtweaks.utils.ViewUtils;
import com.moro.mtweaks.views.recyclerview.DescriptionView;
import com.moro.mtweaks.views.recyclerview.RecyclerViewItem;
import com.moro.mtweaks.views.recyclerview.SwitchView;

import java.util.List;

/**
 * Created by willi on 17.12.16.
 */

public class DataSharingFragment extends RecyclerViewFragment {

    @Override
    protected boolean showBottomFab() {
        return true;
    }

    @Override
    protected Drawable getBottomFabDrawable() {
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(
                getActivity(), R.drawable.ic_search));
        DrawableCompat.setTint(drawable, Color.WHITE);
        return drawable;
    }

    @Override
    protected void onBottomFabClick() {
        super.onBottomFabClick();
        if (!Utils.DONATED) {
            ViewUtils.dialogDonate(getActivity());
            return;
        }

        startActivity(new Intent(getActivity(), DataSharingSearchActivity.class));
    }

    @Override
    protected void init() {
        super.init();

        addViewPagerFragment(DescriptionFragment.newInstance(
                getString(R.string.welcome), getString(R.string.data_sharing_summary)));
        addViewPagerFragment(DescriptionFragment.newInstance(
                getString(R.string.welcome),
                Utils.htmlFrom(getString(R.string.data_sharing_summary_link))));
    }

    @Override
    protected void addItems(List<RecyclerViewItem> items) {
        SwitchView datasharing = new SwitchView();
        datasharing.setSummary(getString(R.string.sharing_enable));
        datasharing.setChecked(Prefs.getBoolean("data_sharing", false, getActivity()));
        datasharing.addOnSwitchListener(new SwitchView.OnSwitchListener() {
            @Override
            public void onChanged(SwitchView switchView, boolean isChecked) {
                if (isChecked) {
                    getActivity().startService(new Intent(getActivity(), Monitor.class));
                } else {
                    getActivity().stopService(new Intent(getActivity(), Monitor.class));
                }
                Prefs.saveBoolean("data_sharing", isChecked, getActivity());
            }
        });

        items.add(datasharing);
    }

    @Override
    protected boolean showAd() {
        return true;
    }

}
