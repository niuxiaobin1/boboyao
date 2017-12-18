package com.xinyi.boboyao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinyi.boboyao.R;
import com.xinyi.boboyao.tools.DensityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Niu on 2017/11/8.
 */

public class BusinessContentAdapter extends RecyclerView.Adapter<BusinessContentAdapter.ViewHolder> {

    private Context context;

    public BusinessContentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_content_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.business_item_PopularityNum_tv.setText("人"+context.getResources()
                .getString(R.string.emptyString)+"气800");

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.business_item_image)
        ImageView business_item_image;

        @BindView(R.id.business_item_ll)
        LinearLayout business_item_ll;

        @BindView(R.id.business_item_name_tv)
        TextView business_item_name_tv;

        @BindView(R.id.business_item_salesNum_tv)
        TextView business_item_salesNum_tv;

        @BindView(R.id.business_item_discountNum_tv)
        TextView business_item_discountNum_tv;

        @BindView(R.id.business_item_praiseNum_tv)
        TextView business_item_praiseNum_tv;

        @BindView(R.id.business_item_distanceNum_tv)
        TextView business_item_distanceNum_tv;

        @BindView(R.id.business_item_PopularityNum_tv)
        TextView business_item_PopularityNum_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            int w = DensityUtils.getScreenWidth(context) - DensityUtils.dp2px(context, 20);

            ViewGroup.LayoutParams params = business_item_image.getLayoutParams();
            params.width = w / 3;
            params.height = w / 4;
            business_item_image.setLayoutParams(params);


            ViewGroup.LayoutParams params1 = business_item_ll.getLayoutParams();

            params1.width = w * 2 / 3;
            params1.height = w / 4;
            business_item_ll.setLayoutParams(params1);

        }
    }

}
