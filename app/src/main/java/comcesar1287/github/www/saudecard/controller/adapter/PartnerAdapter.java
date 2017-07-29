package comcesar1287.github.www.saudecard.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.domain.Partner;
import comcesar1287.github.www.saudecard.controller.interfaces.RecyclerViewOnClickListenerHack;

public class PartnerAdapter extends RecyclerView.Adapter<PartnerAdapter.MyViewHolder>{

    private List<Partner> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context c;

    public PartnerAdapter(Context c, List<Partner> l){
        this.c = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PartnerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.item_partner, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        String address = mList.get(position).getAddress();

        Glide.with(c)
                .load(mList.get((position)).getUrlLogo())
                .into(myViewHolder.picPartner);
        myViewHolder.namePartner.setText(mList.get(position).getName());
        myViewHolder.addressPartner.setText((address.length()>25) ? address.substring(0,25)+"..." : address);
        myViewHolder.segmentPartner.setText(mList.get(position).getSubcategory());
        myViewHolder.discountPartner.setText(mList.get(position).getDiscount());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener /*View.OnCreateContextMenuListener*/{
        ImageView picPartner;
        TextView namePartner, addressPartner, segmentPartner, discountPartner;

        MyViewHolder(View itemView) {
            super(itemView);
            picPartner = (ImageView) itemView.findViewById(R.id.partner_logo);
            namePartner = (TextView) itemView.findViewById(R.id.partner_name);
            addressPartner = (TextView) itemView.findViewById(R.id.partner_address);
            segmentPartner = (TextView) itemView.findViewById(R.id.partner_segment);
            discountPartner = (TextView) itemView.findViewById(R.id.partner_discount);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v, getAdapterPosition());
            }
        }
    }
}
