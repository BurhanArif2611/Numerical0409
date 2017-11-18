package com.revauc.revolutionbuy.ui.buy;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.android.vending.billing.IInAppBillingService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivitySellerOfferDetailBinding;
import com.revauc.revolutionbuy.databinding.ActivitySellerProductDetailBinding;
import com.revauc.revolutionbuy.eventbusmodel.OnButtonClicked;
import com.revauc.revolutionbuy.eventbusmodel.OnPaymentConfirmClicked;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.BuyerCompleteTransactionRequest;
import com.revauc.revolutionbuy.network.request.auth.UnlockPhoneRequest;
import com.revauc.revolutionbuy.network.response.InAppPurchasedModel;
import com.revauc.revolutionbuy.network.response.buyer.BuyerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.UnlockResponse;
import com.revauc.revolutionbuy.network.response.seller.SellerOfferDto;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.buy.adapter.ProductImageAdapter;
import com.revauc.revolutionbuy.ui.sell.OfferSentActivity;
import com.revauc.revolutionbuy.ui.sell.ReportItemActivity;
import com.revauc.revolutionbuy.ui.sell.SellNowActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.LogUtils;
import com.revauc.revolutionbuy.util.StringUtils;
import com.revauc.revolutionbuy.util.Utils;
import com.revauc.revolutionbuy.widget.BottomSheetAlertInverse;
import com.revauc.revolutionbuy.widget.PaymentAmountDialog;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class SellerOfferDetailActivity extends BaseActivity implements View.OnClickListener {


    private ActivitySellerOfferDetailBinding mBinding;
    private SellerOfferDto mProductDetail;
    private static final int REQUEST_REPORT_ITEM = 223;
    int REQ_PURCHASE_PACKAGE = 1001;
    private final BroadcastReceiver mReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    IInAppBillingService mService;
    private final int BILLING_RESPONSE_RESULT_OK = 0;
    private final int BILLING_RESPONSE_RESULT_USER_CANCELED = 1;
    private final int BILLING_RESPONSE_RESULT_NETWORK_PROBLEM = 2;
    private final int BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE = 3;
    private final int BILLING_RESPONSE_RESULT_ERROR = 6;
    private final int BILLING_RESPONSE_RESULT_ALREADY_OWNED = 7;
    private final String PRODUCT_TYPE_IN_APP = "inapp";
    private final String KEY_ITEM_ID_LIST = "ITEM_ID_LIST";
    private final int BILLING_LIB_VERSION = 3;


    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
            getInAppProduct(mService);
        }
    };
    private String mPrice;
    private String mSku;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_seller_offer_detail);

        mProductDetail = getIntent().getParcelableExtra(Constants.EXTRA_PRODUCT_DETAIL);

        mBinding.textTitle.setText(mProductDetail.getBuyerProduct().getTitle());
        mBinding.textCategories.setText(mProductDetail.getBuyerProduct().getBuyerProductCategoriesString() + "");
        mBinding.textPriceOffered.setText((mProductDetail.getDescription().split("&&")[0]) + " " + mProductDetail.getPrice());
        if(mProductDetail.getDescription().split("&&").length>1)
        {
            mBinding.textDescription.setText(mProductDetail.getDescription().split("&&")[1]);
        }
        mBinding.textMobileNo.setText(mProductDetail.getUser().getMobile());

        if (mProductDetail.getSellerProductImages() != null && !mProductDetail.getSellerProductImages().isEmpty()) {
            if (mProductDetail.getSellerProductImages().size() > 1) {
                mBinding.viewpagerImages.setAdapter(new ProductImageAdapter(this, mProductDetail.getSellerProductImages()));
                mBinding.indicatorImages.setViewPager(mBinding.viewpagerImages);
            } else {
                Picasso.with(this).load(mProductDetail.getSellerProductImages().get(0)
                        .getImageName()).placeholder(R.drawable.ic_placeholder_purchase_detail).into(mBinding.imageProduct);
            }
        } else {
            mBinding.imageProduct.setImageResource(R.drawable.ic_placeholder_purchase_detail);
        }

        if (mProductDetail.getUser() != null) {
            mBinding.textSellerName.setText(mProductDetail.getUser().getName());
            mBinding.textSellerLocation.setText(mProductDetail.getUser().getCity().getName() + ", " + mProductDetail.getUser().getCity().getState().getName());
            mBinding.textItemSold.setText("" + mProductDetail.getUser().getSoldCount());
            mBinding.textItemPurchased.setText("" + mProductDetail.getUser().getPurchasedCount());
        }

        mBinding.imageBack.setOnClickListener(this);
        mBinding.textUnlockContactDetails.setOnClickListener(this);
        mBinding.textPay.setOnClickListener(this);
        mBinding.textReportItem.setOnClickListener(this);
        mBinding.textMarkComplete.setOnClickListener(this);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(mReciever, new IntentFilter(ItemPurchasedActivity.BROAD_OFFER_PURCHASED));

        getUnlockStatus(false);

/*
        Binding to the Service.
         */
        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.text_unlock_contact_details:
                getUnlockStatus(true);
                break;
            case R.id.text_mobile:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mProductDetail.getUser().getMobile()));
                startActivity(intent);
                break;
            case R.id.text_pay:
                if (mProductDetail.getUser().getIsSellerAccConnected() == 1) {
                    PaymentAmountDialog.getInstance(this, getString(R.string.payment_amount_dialog_message, mProductDetail.getDescription().split("&&")[0] + " " + Utils.increasePriceByTenPercent(mProductDetail.getPrice())), getString(R.string.proceed), getString(R.string.cancel)).show();
                } else {
                    showSnackBarFromBottom("Seller Account is not connected.", mBinding.mainContainer, true);
                }


                break;
            case R.id.text_mark_complete:
                markBuyerTransactionComplete();
                break;
            case R.id.text_report_item:
                Intent reoprtintent = new Intent(this, ReportItemActivity.class);
                reoprtintent.putExtra(Constants.EXTRA_PRODUCT_ID, mProductDetail.getUserId());
                reoprtintent.putExtra(Constants.EXTRA_IS_BUYER, true);
                startActivityForResult(reoprtintent, REQUEST_REPORT_ITEM);
                break;

        }

    }


    @Subscribe
    public void onPaymentProceedConfirmed(OnPaymentConfirmClicked onPaymentConfirmClicked) {
        Intent payIntent = new Intent(this, PayViaCardActivity.class);
        payIntent.putExtra(Constants.EXTRA_PRODUCT_DETAIL, mProductDetail);
        payIntent.putExtra(Constants.EXTRA_CATEGORY, "" + mProductDetail.getId());
        startActivity(payIntent);
    }


    private void markBuyerTransactionComplete() {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.markBuyerTransactionComplete(new BuyerCompleteTransactionRequest("Paid in Cash", mProductDetail.getBuyerProductId(), mProductDetail.getId(), mProductDetail.getUser().getId())).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

            @Override
            public void onResponse(BaseResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                    startActivity(new Intent(SellerOfferDetailActivity.this, ItemPurchasedActivity.class));
//                    showToast(response.getMessage());
                } else {
                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnackBarFromBottom(errorMessage, mBinding.mainContainer, true);
                }
            }
        });
    }

    private void getUnlockStatus(final boolean showDialog) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getUnlockStatus(mProductDetail.getBuyerProductId(), mProductDetail.getId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<UnlockResponse>(this) {

            @Override
            public void onResponse(UnlockResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                    if (response.getResult().getUnlockPayment() == 1) {
                        mBinding.textUnlockContactDetails.setVisibility(View.GONE);
                        mBinding.textMobile.setVisibility(View.VISIBLE);
                        mBinding.textPay.setVisibility(View.VISIBLE);
                        mBinding.textMarkComplete.setVisibility(View.VISIBLE);
                    } else {
//                        showSnackBarFromBottom("Need to Unlock the payment", mBinding.mainContainer, true);
                        if(showDialog)
                        {
                            if (!StringUtils.isNullOrEmpty(mSku)) {
                                BottomSheetAlertInverse.getInstance(SellerOfferDetailActivity.this,Constants.REQUEST_CODE_CONFIRM_PAY,getString(R.string.unlock_contact_details_message),getString(R.string.yes_pay_now),getString(R.string.cancel)).show();
                            }
                        }
//                        mBinding.textUnlockContactDetails.setVisibility(View.GONE);
//                        mBinding.textMobile.setVisibility(View.VISIBLE);
//                        mBinding.textPay.setVisibility(View.VISIBLE);
//                        mBinding.textMarkComplete.setVisibility(View.VISIBLE);
//                        mBinding.textSellerLocation.setText(mProductDetail.getUser().getCity().getName()+", "+mProductDetail.getUser().getCity().getState().getName()+", "+mProductDetail.getUser().getEmail());
                    }
                } else {
                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnackBarFromBottom(errorMessage, mBinding.mainContainer, true);
                }
            }
        });
    }

    @Subscribe
    public void onPayConfirm(OnButtonClicked onDeleteClicked)
    {
        if(onDeleteClicked.getRequestCode()==Constants.REQUEST_CODE_CONFIRM_PAY)
        {
            buyPackage(mService, mSku);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onDestroy() {
        if (mServiceConn != null) {
            unbindService(mServiceConn);
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReciever);
    }

    public void getInAppProduct(IInAppBillingService service) {
        mPrice = null;
        mSku = null;
        ArrayList<String> skuList = new ArrayList<>();
        skuList.add("unlock_details");

        Bundle querySkus = new Bundle();
        querySkus.putStringArrayList(KEY_ITEM_ID_LIST, skuList);

        try {
            Bundle skuDetails = service.getSkuDetails(BILLING_LIB_VERSION,
                    getPackageName(),
                    PRODUCT_TYPE_IN_APP,
                    querySkus);

            int response = skuDetails.getInt("RESPONSE_CODE");

            if (checkResponseIsSuccess(response)) {
                ArrayList<String> responseList
                        = skuDetails.getStringArrayList("DETAILS_LIST");

                for (String thisResponse : responseList) {
                    JSONObject object = new JSONObject(thisResponse);
                    String sku = object.getString("productId");
                    String price = object.getString("price");
                    if (sku.equals("unlock_details")) {
                        mPrice = price;
                        mSku = sku;
                    }

                    LogUtils.LOGD(">>>IN APP>>", "PRICE : " + mPrice);


                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            mListener.onHideProgress();
        }
    }

    public void buyPackage(IInAppBillingService service, String productCode) {

        try {
            Bundle buyIntentBundle = service.getBuyIntent(BILLING_LIB_VERSION,
                    getPackageName(),
                    productCode,
                    PRODUCT_TYPE_IN_APP,
                    null);

            int response = buyIntentBundle.getInt("RESPONSE_CODE");

            /*
            If while purchasing the in app library tells that this package is already owned
            then we cross verify this buy querying the server for the purchased items with transaction receipt.
             */
            if (checkResponseIsSuccess(response)) {
                PendingIntent pendingIntent = buyIntentBundle.getParcelable("BUY_INTENT");

                startIntentSenderForResult(pendingIntent.getIntentSender(),
                        REQ_PURCHASE_PACKAGE,
                        new Intent(),
                        0,
                        0,
                        0);

            } else if (response == BILLING_RESPONSE_RESULT_ALREADY_OWNED) {
                getAllOwnedItems(service, productCode);
            }

        } catch (RemoteException | IntentSender.SendIntentException e) {
            e.printStackTrace();
//             mListener.onHideProgress();
        }
    }

    private void getAllOwnedItems(IInAppBillingService service, String productCode) {
        try {

            if(service == null){
                return;
            }

            /*
            This method returns the current un-consumed products owned by the user, including both purchased items and items acquired by redeeming a promo code
             */
            Bundle ownedItems = service.getPurchases(BILLING_LIB_VERSION,
                    getPackageName(),
                    PRODUCT_TYPE_IN_APP,
                    null);

            int response = ownedItems.getInt("RESPONSE_CODE");
            if (checkResponseIsSuccess(response)) {
                ArrayList<String> purchaseDataList = ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");

                ArrayList<InAppPurchasedModel> data = new Gson().fromJson(purchaseDataList.toString(),
                        new TypeToken<List<InAppPurchasedModel>>() {
                        }.getType());


                for (InAppPurchasedModel model : data) {

                    /*
                    The non-consumed product code which we need to ensure it is available on server or not.
                     */
                    consumeProducts(service,model.getOrderId(),model.getPurchaseToken());
                    break;
                }


            }


        } catch (RemoteException e) {
            e.printStackTrace();
            hideProgressBar();
        }
    }

    public void consumeProducts(IInAppBillingService mService,String orderId,String purchaseToken) {
        if(mService == null){
            return;
        }

        showProgressBar();
        try {
            int response = mService.consumePurchase(BILLING_LIB_VERSION, getPackageName(), purchaseToken);
            if (checkResponseIsSuccess(response)) {
                    unlockContactDetails(orderId,purchaseToken);
            }

        } catch (RemoteException e) {
            e.printStackTrace();

        }finally {
            hideProgressBar();
        }
    }

    private boolean checkResponseIsSuccess(int responseCode) {
        boolean isSuccess = false;

        switch (responseCode) {

            case BILLING_RESPONSE_RESULT_OK:
                isSuccess = true;
                break;

            case BILLING_RESPONSE_RESULT_USER_CANCELED:
                showSnackBarFromBottom("Transaction cancelled or back button pressed", mBinding.mainContainer, true);
                break;

            case BILLING_RESPONSE_RESULT_NETWORK_PROBLEM:
                showSnackBarFromBottom(getString(R.string.no_internet), mBinding.mainContainer, true);
                break;

            case BILLING_RESPONSE_RESULT_ERROR:
            case BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE:
                showSnackBarFromBottom("Please check your Google PlayStore settings and try again", mBinding.mainContainer, true);
                break;

//            default:
//                showSnackBarFromBottom("There seems to be some error. Please try again later", mBinding.mainContainer, true);
//                break;
        }

        return isSuccess;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_REPORT_ITEM) {
            if (resultCode == Activity.RESULT_OK) {
                showSnackBarFromBottom(getString(R.string.report_sent), mBinding.mainContainer, false);
            }
        }
        if (requestCode == REQ_PURCHASE_PACKAGE) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);


            if (checkResponseIsSuccess(responseCode) && resultCode == RESULT_OK) {
                LogUtils.LOGD(">>>IN APP>>", "PRODUCT PURCHASED");
                String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
                InAppPurchasedModel purchasedModel = new Gson().fromJson(purchaseData, InAppPurchasedModel.class);
//                String purchaseToken = purchasedModel.getPurchaseToken();
//
//                int response = 0;
//                try {
//                    response = mService.consumePurchase(BILLING_LIB_VERSION, getPackageName(), purchaseToken);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//                if (checkResponseIsSuccess(response)) {
//                    LogUtils.LOGD(">>>IN APP>>", "PRODUCT CONSUMED");

                    unlockContactDetails(purchasedModel.getOrderId(),purchasedModel.getPurchaseToken());
//                }
            }

        }
    }

    private void unlockContactDetails(String transactionId, final String transactionReciept) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.unlockPhoneDetails(new UnlockPhoneRequest(mProductDetail.getBuyerProductId(), mProductDetail.getUserId(), transactionId, transactionReciept)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

            @Override
            public void onResponse(BaseResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                    int responseCode = 0;
                    try {
                        responseCode = mService.consumePurchase(BILLING_LIB_VERSION, getPackageName(), transactionReciept);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    if (checkResponseIsSuccess(responseCode)) {
                        LogUtils.LOGD(">>>IN APP>>", "PRODUCT CONSUMED");
                    }
                    mBinding.textUnlockContactDetails.setVisibility(View.GONE);
                    mBinding.textMobile.setVisibility(View.VISIBLE);
                    mBinding.textPay.setVisibility(View.VISIBLE);
                    mBinding.textMarkComplete.setVisibility(View.VISIBLE);
                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, false);
                } else {
                    showSnackBarFromBottom(response.getMessage(), mBinding.mainContainer, true);
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnackBarFromBottom(errorMessage, mBinding.mainContainer, true);
                }
            }
        });
    }

}

