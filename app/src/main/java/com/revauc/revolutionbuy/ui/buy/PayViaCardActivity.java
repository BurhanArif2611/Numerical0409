package com.revauc.revolutionbuy.ui.buy;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.revauc.revolutionbuy.BuildConfig;
import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.databinding.ActivityMobileVerificationBinding;
import com.revauc.revolutionbuy.databinding.ActivityPayCardBinding;
import com.revauc.revolutionbuy.network.BaseResponse;
import com.revauc.revolutionbuy.network.RequestController;
import com.revauc.revolutionbuy.network.request.auth.BuyerCompleteTransactionRequest;
import com.revauc.revolutionbuy.network.request.auth.BuyerPaymentRequest;
import com.revauc.revolutionbuy.network.request.auth.MobilePinRequest;
import com.revauc.revolutionbuy.network.response.buyer.SellerProductDto;
import com.revauc.revolutionbuy.network.response.buyer.UnlockResponse;
import com.revauc.revolutionbuy.network.response.seller.SellerOfferDto;
import com.revauc.revolutionbuy.network.retrofit.AuthWebServices;
import com.revauc.revolutionbuy.network.retrofit.DefaultApiObserver;
import com.revauc.revolutionbuy.ui.BaseActivity;
import com.revauc.revolutionbuy.ui.auth.MobilePinVerificationActivity;
import com.revauc.revolutionbuy.util.Constants;
import com.revauc.revolutionbuy.util.LogUtils;
import com.revauc.revolutionbuy.util.StringUtils;
import com.revauc.revolutionbuy.util.Utils;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.security.AccessController.getContext;


public class PayViaCardActivity extends BaseActivity implements View.OnClickListener {


    private ActivityPayCardBinding mBinding;
    private SellerOfferDto mProductDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_pay_card);

        mProductDetail = getIntent().getParcelableExtra(Constants.EXTRA_PRODUCT_DETAIL);

        mBinding.toolbarProfile.tvToolbarGeneralLeft.setText(R.string.cancel);
        mBinding.toolbarProfile.tvToolbarGeneralLeft.setVisibility(View.VISIBLE);
        mBinding.toolbarProfile.tvToolbarGeneralLeft.setOnClickListener(this);
        mBinding.toolbarProfile.txvToolbarGeneralCenter.setText(R.string.pay_via_credit_card);
        mBinding.textContinue.setOnClickListener(this);


    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tv_toolbar_general_left:
                onBackPressed();

                break;
            case R.id.text_continue:
                attemptCardPay();
                break;
        }

    }

    private void attemptCardPay() {
        String cardNumber = mBinding.editCardNo.getText().toString();
        int[] dateFields = mBinding.editExpiry.getValidDateFields();
        int month = 0;
        int year = 0;
        if(dateFields!=null)
        {
            if(dateFields.length==2)
            {
                month = dateFields[0];
                year = dateFields[1];
            }
        }
        String cvc = mBinding.editCvv.getText().toString();
        Card card = new Card(cardNumber, month, year, cvc);

        if (!card.validateCard()) {
            showSnackBarFromBottom("Invalid Card Data",mBinding.mainContainer,true);
        }
        else
        {
            Stripe stripe = new Stripe(this, BuildConfig.STRIPE_PUBLISH_KEY);
            showProgressBar();
            stripe.createToken(
                    card,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            hideProgressBar();
                            // Send token to your server
                            LogUtils.LOGD(">>>>>","TOKEN GENERATED : "+token.getId());
                            convertCurrency(mProductDetail.getDescription().split("&&")[0], Utils.increasePriceByTenPercent(mProductDetail.getPrice()),token.getId());

                        }
                        public void onError(Exception error) {
                            // Show localized error message
                            hideProgressBar();
                            showSnackBarFromBottom(error.getLocalizedMessage(),mBinding.mainContainer,true);
                        }
                    }
            );
        }
    }

    private void convertCurrency(String selectedCurrency, String finalPrice, final String stripeToken) {

        LogUtils.LOGD(">>>>>>>","FINAL AMT : "+finalPrice);
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.getConvertedAmount(finalPrice,selectedCurrency,"AUD").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<ResponseBody>(this) {

            @Override
            public void onResponse(ResponseBody response) {
                hideProgressBar();
                String convertedStringResponse = null;
                int convertedAmount;
                try {
                    convertedStringResponse = response.string();
                } catch (IOException e) {

                }
//                LogUtils.LOGD(">>>>>>>","RESPONSE BODY : "+convertedStringResponse);

                int startIndex = convertedStringResponse.indexOf("<span class=bld>");
                int endIndex = convertedStringResponse.indexOf("AUD</span>");

                String value = convertedStringResponse.substring(startIndex+16,endIndex);
                if(value!=null)
                {
                    convertedAmount = (int)(Double.parseDouble(value.trim())*100);
                }
                else
                {
                    convertedAmount =0;
                }
                LogUtils.LOGD(">>>>>>>","CONVERTED AMT : "+convertedAmount);

//                Utils.increasePriceByTenPercent(

                makeProductPayment(new BuyerPaymentRequest(stripeToken,convertedAmount,(int)Float.parseFloat(Utils.deccreasePriceByTenPercent(convertedAmount)),mProductDetail.getId(),mProductDetail.getUserId(),mProductDetail.getBuyerProductId()));
            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnackBarFromBottom(errorMessage,mBinding.mainContainer, true);
                }
            }
        });
    }

    private void makeProductPayment(BuyerPaymentRequest buyerPaymentRequest) {
        showProgressBar();
        AuthWebServices apiService = RequestController.createRetrofitRequest(false);

        apiService.processProductPayment(buyerPaymentRequest).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultApiObserver<BaseResponse>(this) {

            @Override
            public void onResponse(BaseResponse response) {
                hideProgressBar();
                if (response.isSuccess()) {
                    finish();
                    showToast(response.getMessage());
                }
                else
                {
                    showSnackBarFromBottom(response.getMessage(),mBinding.mainContainer, true);
                }

            }

            @Override
            public void onError(Throwable call, BaseResponse baseResponse) {
                hideProgressBar();
                if (baseResponse != null) {
                    String errorMessage = baseResponse.getMessage();
                    showSnackBarFromBottom(errorMessage,mBinding.mainContainer, true);
                }
            }
        });
    }


}
