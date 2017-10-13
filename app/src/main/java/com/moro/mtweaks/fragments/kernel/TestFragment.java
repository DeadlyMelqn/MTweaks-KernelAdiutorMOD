package com.moro.mtweaks.fragments.kernel;

import com.moro.mtweaks.R;
import com.moro.mtweaks.fragments.RecyclerViewFragment;
import com.moro.mtweaks.utils.Device;
import com.moro.mtweaks.views.recyclerview.CardView;
import com.moro.mtweaks.views.recyclerview.ProgressBarView;
import com.moro.mtweaks.views.recyclerview.RecyclerViewItem;

import java.util.List;

/**
 * Created by MoroGoku on 02/10/2017.
 */

public class TestFragment extends RecyclerViewFragment {

    private ProgressBarView swap;
    private ProgressBarView mem;

    @Override
    protected boolean showViewPager() {
        return false;
    }

    @Override
    protected void addItems(List<RecyclerViewItem> items) {

        CardView card = new CardView(getActivity());
        card.setTitle(getString(R.string.memory));

        long swap_total = Device.MemInfo.getItemMb("SwapTotal");
        long swap_progress = swap_total - Device.MemInfo.getItemMb("SwapFree");

        swap = new ProgressBarView();
        swap.setTitle("SWAP");
        swap.setItems(swap_total, swap_progress);
        swap.setUnit(getResources().getString(R.string.mb));
        swap.setProgressColor(getResources().getColor(R.color.blue_accent));
        swap.showPercent(false);
        card.addItem(swap);

        long mem_total = Device.MemInfo.getItemMb("MemTotal");
        long mem_progress = mem_total - (Device.MemInfo.getItemMb("Cached") + Device.MemInfo.getItemMb("MemFree"));

        mem = new ProgressBarView();
        mem.setTitle("RAM");
        mem.setItems(mem_total, mem_progress);
        mem.setUnit(getResources().getString(R.string.mb));
        mem.setProgressColor(getResources().getColor(R.color.orange_accent));
        card.addItem(mem);

        items.add(card);
    }

    @Override
    protected void refresh() {
        super.refresh();

        if (swap != null) {
            long total = Device.MemInfo.getItemMb("SwapTotal");
            long progress = total - Device.MemInfo.getItemMb("SwapFree");
            swap.setItems(total, progress);
        }
        if (mem != null) {
            long total = Device.MemInfo.getItemMb("MemTotal");
            long progress = total - (Device.MemInfo.getItemMb("Cached") + Device.MemInfo.getItemMb("MemFree"));
            mem.setItems(total, progress);
        }
    }

}
