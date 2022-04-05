package com.lovezly.coach.activity.order;

import com.lovezly.coach.activity.adapter.OrderAdapter;
import com.lovezly.coach.util.DemoUtils;

public class OrderAllFragment extends OrderInitFragment<OrderAdapter> {


    public OrderAllFragment(int tabStatus) {
        super(tabStatus);
    }

    @Override
    protected OrderAdapter initAdapter() {
        return new OrderAdapter(null);
    }
}
