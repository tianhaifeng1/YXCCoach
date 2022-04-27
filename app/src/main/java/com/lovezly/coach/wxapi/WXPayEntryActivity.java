package com.lovezly.coach.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.module_common.official.OfficialMVPActivity;
import com.lovezly.coach.activity.order.OrderDetailActivity;
import com.lovezly.coach.assist.ReturnPayResult;
import com.lovezly.coach.databinding.ActivityPayWinBinding;
import com.lovezly.coach.databinding.ActivityWelcomeBinding;
import com.lovezly.coach.util.DemoConstant;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends OfficialMVPActivity<WXEntryView, WXEntryPresenter, ActivityPayWinBinding> implements WXEntryView, IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void initView() {
        super.initView();
        api = WXAPIFactory.createWXAPI(this, DemoConstant.wx_app_id);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected ActivityPayWinBinding getViewBinding() {
        return ActivityPayWinBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    protected WXEntryPresenter initPersenter() {
        return new WXEntryPresenter(this);
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("tian===", "onPayFinish, errCode = " + resp.errCode);
        EventBus.getDefault().post(new ReturnPayResult("WXPayEntryActivity"));
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
                //支付成功
                ToastUtils.showShort("支付成功");
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("id", DemoConstant.order);
                skipActivity(intent);
                finish();
            } else if (resp.errCode == BaseResp.ErrCode.ERR_COMM) {
                //支付失败
                ToastUtils.showShort("支付失败");
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("id", DemoConstant.order);
                skipActivity(intent);
                finish();
            } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {
                //支付取消
                ToastUtils.showShort("支付取消");
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("id", DemoConstant.order);
                skipActivity(intent);
                finish();
            }
        }
    }
}