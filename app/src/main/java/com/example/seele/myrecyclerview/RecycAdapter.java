package com.example.seele.myrecyclerview;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seele.myrecyclerview.helper.ItemTouchHelperAdapter;
import com.example.seele.myrecyclerview.helper.ItemTouchHelperViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by SEELE on 2016/8/8.
 */
public class RecycAdapter extends RecyclerView.Adapter<RecycAdapter.TextViewHolder> implements ItemTouchHelperAdapter {//, View.OnFocusChangeListener
    private Context context;
    private List<poju> list1;

    public RecycAdapter(Context context) {
        this.context = context;
        list1 = new ArrayList<>();
    }

    public void addData(List<poju> list, LinearLayoutManager linearLayoutManager) {
        //传一个包括type的实体类
        this.list1 = list;
        notifyItemInserted(list1.size() - 1);
        notifyItemRangeChanged(list1.size() - 1, list1.size());
        linearLayoutManager.scrollToPosition(list.size() - 1);

    }


    @Override
    public RecycAdapter.TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item1, parent, false);
        TextViewHolder textViewHolder = new TextViewHolder(view);
        return textViewHolder;

    }

    @Override
    public void onBindViewHolder(final TextViewHolder holder, int position) {
        Log.i("tag", "-------------move--.>> onBind---");
//        禁止复用
//        holder.setIsRecyclable(false);
        int i = holder.getAdapterPosition();
        if (list1.get(i).type == 100) {
            holder.imageBlock.setVisibility(View.VISIBLE);
            holder.editBlock.setVisibility(View.GONE);
            if (list1.get(i).bitmap != null) {
                holder.imageView.setImageBitmap(list1.get(i).bitmap);
            }
            holder.imageDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(holder);
                }
            });
        } else {
            holder.editText.setTag(position);

            holder.editBlock.setVisibility(View.VISIBLE);
            if (position == (int) holder.editText.getTag()) {
                holder.editText.setText(list1.get(position).editData);
            }
            holder.editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int i = (int) holder.editText.getTag();
                    poju poju1 = list1.get(i);
                    poju1.editData = String.valueOf(s);
                    list1.set(i, poju1);
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            holder.editDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(holder);
                }
            });
            holder.imageBlock.setVisibility(View.GONE);
        }
    }

    private void removeItem(final TextViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确认删除吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                int i = holder.getAdapterPosition();
                if(i < list1.size()){
                    list1.remove(i);
                    notifyItemRemoved(i);
                }else{
                    notifyDataSetChanged();
                }
                if (i != list1.size()) { // 如果移除的是最后一个，忽略
                    notifyItemRangeChanged(i, list1.size() - i);
//                    notifyDataSetChanged();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    @Override
    public int getItemCount() {
        return list1.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Log.i("tag", fromPosition + "--------" + toPosition);
        Collections.swap(list1, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        notifyItemRangeChanged(fromPosition, 2);//新加
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        list1.remove(position);
        notifyItemRemoved(position);
    }






    public class TextViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{
        private EditText editText;
        private FrameLayout editBlock;
        private Button editDel;

        private ImageView imageView;
        private FrameLayout imageBlock;
        private Button imageDel;

        public TextViewHolder(View itemView) {
            super(itemView);
            editText = (EditText) itemView.findViewById(R.id.editText);
            editBlock = (FrameLayout) itemView.findViewById(R.id.edit_block);
            editDel = (Button) itemView.findViewById(R.id.edit_del);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageBlock = (FrameLayout) itemView.findViewById(R.id.imageBlock);
            imageDel = (Button) itemView.findViewById(R.id.image_del);


        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

}
//public class RecycAdapter extends RecyclerView.Adapter<RecycAdapter.ViewHolder> implements ItemTouchHelperAdapter {//, View.OnFocusChangeListener
//    private Context context;
//    private List<poju> list1;
//
//    public RecycAdapter(Context context) {
//        this.context = context;
//        list1 = new ArrayList<>();
//    }
//
//    public void addData(List<poju> list, LinearLayoutManager linearLayoutManager) {
//        //传一个包括type的实体类
//        this.list1 = list;
//        notifyItemInserted(list1.size() - 1);
//        notifyItemRangeChanged(list1.size() - 1, list1.size());
//        linearLayoutManager.scrollToPosition(list.size() - 1);
//
//    }
//
//
//    @Override
//    public RecycAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item1, parent, false);
//        ViewHolder vh = new ViewHolder(view, new MyCustomEditTextListener());
//        return vh;
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        int i = holder.getAdapterPosition();
//        if (list1.get(i).type == 100) {
//            holder.imageBlock.setVisibility(View.VISIBLE);
//            holder.editBlock.setVisibility(View.GONE);
//            if (list1.get(i).bitmap != null) {
//                holder.imageView.setImageBitmap(list1.get(i).bitmap);
//            }
//            holder.imageDel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setMessage("确认删除吗？");
//                    builder.setTitle("提示");
//                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            int i = holder.getAdapterPosition();
//                            list1.remove(i);
//                            notifyItemRemoved(i);
//
//                            if (i != list1.size()) { // 如果移除的是最后一个，忽略
//                                notifyDataSetChanged();
//                            }
//                        }
//                    });
//                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.create().show();
//                }
//            });
//        } else {
//            holder.editBlock.setVisibility(View.VISIBLE);
//            holder.myCustomEditTextListener.updatePosition(position);
//            holder.editText.setText(list1.get(position).editData);
//            holder.editDel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setMessage("确认删除吗？");
//                    builder.setTitle("提示");
//                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                            int i = holder.getAdapterPosition();
//                            list1.remove(i);
//                            notifyItemRemoved(i);
//                            if (i != list1.size()) { // 如果移除的是最后一个，忽略
//                                notifyItemRangeChanged(i, list1.size() - i);
//                            }
//                        }
//                    });
//                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.create().show();
//                }
//            });
//            holder.imageBlock.setVisibility(View.GONE);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return list1.size();
//    }
//
//    @Override
//    public boolean onItemMove(int fromPosition, int toPosition) {
//        Log.i("tag", fromPosition + "--------" + toPosition);
//        Collections.swap(list1, fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
//        return true;
//    }
//
//    @Override
//    public void onItemDismiss(int position) {
//        list1.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        private EditText editText;
//        private FrameLayout editBlock;
//        private Button editDel;
//
//        private ImageView imageView;
//        private FrameLayout imageBlock;
//        private Button imageDel;
//
//        public MyCustomEditTextListener myCustomEditTextListener;
//
//        public ViewHolder(View v, MyCustomEditTextListener myCustomEditTextListener) {
//            super(v);
//
//
//            this.editBlock = (FrameLayout) itemView.findViewById(R.id.edit_block);
//            this.editDel = (Button) itemView.findViewById(R.id.edit_del);
//
//            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
//            this.imageBlock = (FrameLayout) itemView.findViewById(R.id.imageBlock);
//            this.imageDel = (Button) itemView.findViewById(R.id.image_del);
//
//
//            this.editText = (EditText) v.findViewById(R.id.editText);
//            this.myCustomEditTextListener = myCustomEditTextListener;
//            this.editText.addTextChangedListener(myCustomEditTextListener);
//        }
//    }
//
//    // we make TextWatcher to be aware of the position it currently works with
//    // this way, once a new item is attached in onBindViewHolder, it will
//    // update current position MyCustomEditTextListener, reference to which is kept by ViewHolder
//    private class MyCustomEditTextListener implements TextWatcher {
//        private int position;
//
//        public void updatePosition(int position) {
//
//            Log.i("tag6", position + "____________");
//            this.position = position;
//        }
//
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//            // no op
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//
//            Log.i("tag6", position + "______onTextChanged______");
//            list1.get(position).editData = charSequence.toString();
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//            // no op
//        }
//    }
//
//
//}