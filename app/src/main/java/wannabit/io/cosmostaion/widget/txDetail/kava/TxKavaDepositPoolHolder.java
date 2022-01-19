package wannabit.io.cosmostaion.widget.txDetail.kava;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cosmos.base.abci.v1beta1.Abci;
import cosmos.tx.v1beta1.ServiceOuterClass;
import kava.swap.v1beta1.Tx;
import wannabit.io.cosmostaion.R;
import wannabit.io.cosmostaion.base.BaseChain;
import wannabit.io.cosmostaion.base.BaseData;
import wannabit.io.cosmostaion.model.type.Coin;
import wannabit.io.cosmostaion.utils.WDp;
import wannabit.io.cosmostaion.utils.WLog;
import wannabit.io.cosmostaion.widget.txDetail.TxHolder;

public class TxKavaDepositPoolHolder extends TxHolder {
    ImageView   itemDepositCoinImg;
    TextView    itemDepositCoinSender,
                itemDepositTokenInAmount0, itemDepositTokenInSymbol0, itemDepositTokenInAmount1, itemDepositTokenInSymbol1;

    public TxKavaDepositPoolHolder(@NonNull View itemView) {
        super(itemView);
        itemDepositCoinImg          = itemView.findViewById(R.id.tx_join_pool_icon);
        itemDepositCoinSender       = itemView.findViewById(R.id.tx_join_pool_sender);
        itemDepositTokenInAmount0   = itemView.findViewById(R.id.tx_token_in_amount1);
        itemDepositTokenInSymbol0   = itemView.findViewById(R.id.tx_token_in_symbol1);
        itemDepositTokenInAmount1   = itemView.findViewById(R.id.tx_token_in_amount2);
        itemDepositTokenInSymbol1   = itemView.findViewById(R.id.tx_token_in_symbol2);
    }

    public void onBindMsg(Context c, BaseData baseData, BaseChain baseChain, ServiceOuterClass.GetTxResponse response, int position, String address, boolean isGen) {
        itemDepositCoinImg.setColorFilter(WDp.getChainColor(c, baseChain), android.graphics.PorterDuff.Mode.SRC_IN);

        try {
            Tx.MsgDeposit msg = Tx.MsgDeposit.parseFrom(response.getTx().getBody().getMessages(position).getValue());
            itemDepositCoinSender.setText(msg.getDepositor());

            Coin inCoin0 = null;
            Coin inCoin1 = null;
            if (response.getTxResponse().getLogsCount() > position) {
                for (Abci.StringEvent event : response.getTxResponse().getLogs(position).getEventsList()) {
                    if (event.getType().equals("transfer")) {
                        String InValue0 = event.getAttributesList().get(2).getValue().split(",")[0];
                        String InValue1 = event.getAttributesList().get(2).getValue().split(",")[1];

                        String coin0Amount = "";
                        if (InValue0.contains("ibc")) {
                            coin0Amount = InValue0.split("ibc")[0];
                        } else {
                            coin0Amount = InValue0.replaceAll("[^0-9]", "");
                        }
                        inCoin0 = new Coin(InValue0.replaceAll(coin0Amount, ""), coin0Amount);

                        String coin1Amount = "";
                        if (InValue1.contains("ibc")) {
                            coin1Amount = InValue1.split("ibc")[0];
                        } else {
                            coin1Amount = InValue1.replaceAll("[^0-9]", "");
                        }
                        inCoin1 = new Coin(InValue1.replaceAll(coin1Amount, ""), coin1Amount);
                    }
                }
            }
            if (inCoin0 != null && inCoin1 != null) {
                WDp.showCoinDp(c, baseData, inCoin0, itemDepositTokenInSymbol0, itemDepositTokenInAmount0, baseChain);
                WDp.showCoinDp(c, baseData, inCoin1, itemDepositTokenInSymbol1, itemDepositTokenInAmount1, baseChain);
            } else {
                itemDepositTokenInAmount0.setText("");
                itemDepositTokenInSymbol0.setText("");
                itemDepositTokenInAmount1.setText("");
                itemDepositTokenInSymbol1.setText("");
            }
        } catch (Exception e) {
            WLog.w("Exception " + e.getMessage());
        }
    }
}