package com.hl5pma.rxexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnLoadMoreListener {
        public void onLoadMore();
    }

    private static final int ITEM_VIEW_TYPE_IMAGE = 1;
    private static final int ITEM_VIEW_TYPE_LOAD_MORE = 2;

    private List<ImageSearchResult.Channel.Item> mItems = new ArrayList<>();

    private Context mContext;
    private LayoutInflater mInflater;

    private OnLoadMoreListener mLoadMoreListener;

    public ImageAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void clear() {
        mItems.clear();
        mIsLoading = false;
        mHasMore = false;
        notifyDataSetChanged();
    }

    public void addItems(List<ImageSearchResult.Channel.Item> items) {
        final int positionStart = mItems.size();
        final int itemCount = items.size();
        mItems.addAll(items);
        notifyItemRangeInserted(positionStart, itemCount);

        mIsLoading = false;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_IMAGE) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) ((RecyclerView) viewGroup).getLayoutManager();
            final int spanCount = layoutManager.getSpanCount();
            final int itemWidth = (int) ((float) viewGroup.getMeasuredWidth() / spanCount + 0.5f);

            View itemView = mInflater.inflate(R.layout.item_item, viewGroup, false);

            return new ItemViewHolder(itemView, itemWidth);
        } else if (viewType == ITEM_VIEW_TYPE_LOAD_MORE) {
            View itemView = mInflater.inflate(R.layout.load_more_item, viewGroup, false);
            ViewGroup.LayoutParams params = itemView.getLayoutParams();
            if (params instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) params).setFullSpan(true);
            }
            return new LoadMoreViewHolder(itemView);
        }

        return null;
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_IMAGE) {
            ItemViewHolder holder = (ItemViewHolder) viewHolder;
            holder.apply(mItems.get(position));
        }
    }

    @Override public int getItemCount() {
        if (mHasMore) {
            return mItems.size() + 1;
        } else {
            return mItems.size();
        }
    }

    public void setHasMore(boolean hasMore) {
        mHasMore = hasMore;
        notifyDataSetChanged();
    }

    private boolean mIsLoading;
    private boolean mHasMore;

    @Override public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mIsLoading) {
                    return;
                }

                final StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                final int[] lastVisiblePositions = new int[layoutManager.getSpanCount()];
                layoutManager.findLastVisibleItemPositions(lastVisiblePositions);
                for (final int position : lastVisiblePositions) {
                    if (getItemViewType(position) == ITEM_VIEW_TYPE_LOAD_MORE) {
                        mIsLoading = true;
                        if (mLoadMoreListener != null) {
                            mLoadMoreListener.onLoadMore();
                        }
                    }
                }


            }
        });
    }

    @Override public int getItemViewType(int position) {
        if (mHasMore) {
            if (position < mItems.size()) {
                return ITEM_VIEW_TYPE_IMAGE;
            } else {
                return ITEM_VIEW_TYPE_LOAD_MORE;
            }
        } else {
            return ITEM_VIEW_TYPE_IMAGE;
        }
    }

    public void setLoadMoreListener(OnLoadMoreListener listener) {
        mLoadMoreListener = listener;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImage;

        private int mImageWidth;

        public ItemViewHolder(View itemView, int imageWidth) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.image);
            mImageWidth = imageWidth;
        }

        public void apply(ImageSearchResult.Channel.Item item) {
            mImage.getLayoutParams().height = calcImageViewHeight(item.width, item.height);

            Glide.with(itemView.getContext())
                    .load(item.image)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mImage);
        }

        private int calcImageViewHeight(int originalWidth, int originalHeight) {
            return (int) (mImageWidth * ((float) originalHeight / originalWidth));
        }
    }

    static class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }
}
