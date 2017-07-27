package comcesar1287.github.www.saudecard.controller.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.domain.Categorie;
import comcesar1287.github.www.saudecard.controller.interfaces.RecyclerViewOnClickListenerHack;


public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.MyViewHolder>{

    private List<Categorie> mList;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    private Context c;

    public CategorieAdapter(Context c, List<Categorie> l){
        this.c = c;
        mList = l;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CategorieAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.item_categorie, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {

        Glide.with(c)
                .load(mList.get((position)).getIcon())
                .into(myViewHolder.iconCategorie);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        mRecyclerViewOnClickListenerHack = r;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener /*View.OnCreateContextMenuListener*/{
        ImageView iconCategorie;

        MyViewHolder(View itemView) {
            super(itemView);
            iconCategorie = (ImageView) itemView.findViewById(R.id.categorie_icon);

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
