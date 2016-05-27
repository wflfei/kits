package com.wfl.kits.widget.tagsview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wfl.kits.BuildConfig;
import com.wfl.kits.R;
import com.wfl.kits.commons.utils.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 搜索时居中对齐的关键词控件，同时该控件支持折叠成固定几行显示和展开所有
 * @author wfl
 */
public class TagsView extends LinearLayout {
	public static final String TAG = Logger.makeLogTag(TagsView.class);
	private List<TagItem> tags;
	/**
	 * 用来缓存测量过的字符串的宽度
	 */
	private Map<String, Integer> cacheWidthMap;

	OnTagItemClickListener mOnTagItemClicktener;

	/**
	 * 每个item的layout id
	 */
	private int itemLayoutId = R.layout.tag_textview;
	private int textViewId = R.id.tagtext;

	/**
	 * 可用宽度（除去padding的宽度）
	 */
	private int rowWidth = 0;
	private int rowHeight = 0;
	int row = 0;
	private int rowsNum;
	int currentWidth = 0;
	/**
	 * measure的View高度
	 */
	private int viewHeight = 0;
	private int paddBottom = 0;
	/**
	 * 在parent中的位置
	 */
	private int position;

	/**
	 * 完全展开的高度
	 */
	private int expandedHeight = 0;
	/**
	 * 可折叠时折叠后的高度
	 */
	private int collpasedRowsNum = 2;
	/**
	 * 设置该值表示不需要折叠并且限制只显示这么多行，超过该行的不显示。
	 */
	private int limitedRowNum = -1;
	private boolean needExpanded = false;
	private boolean expanded = true;
	private TagsMeasuredCallBack tagsMeasuredCallBack;


	public TagsView(Context context) {
		super(context);
		init(null);
	}

	public TagsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	@SuppressLint("NewApi")
	public TagsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	private void init(AttributeSet attrs) {
		paddBottom = super.getPaddingBottom();
		super.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), 0);
		cacheWidthMap = new HashMap<>();
		if (null != attrs) {
			TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TagsView, 0, 0);
			itemLayoutId = a.getResourceId(R.styleable.TagsView_itemLayout, R.layout.tag_textview);
			textViewId = a.getResourceId(R.styleable.TagsView_textviewId, R.id.tagtext);
			a.recycle();
		}
	}

	/**
	 * 覆盖getPaddingBottom以获取自定义配置的paddingBottom，而不是View的
	 * @return
	 */
	@Override
	public int getPaddingBottom() {
		return paddBottom;
	}

	/**
	 * 设置在父控件中的位置s
	 * @param position
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public void setCollapsedRowsNum(int collapsedNum) {
		collpasedRowsNum = collapsedNum;
	}

	/**
	 * 设置限制只显示 limitedRowNum 行，并且不可折叠和扩展
	 * @param limitedRowNum
	 */
	public void setLimitedRowNum(int limitedRowNum) {
		this.limitedRowNum = limitedRowNum;
	}

	public boolean isNeedExpanded() {
		return needExpanded;
	}

	public int getCollapsedHeight() {
		return rowHeight * collpasedRowsNum + rowHeight / 5 + getPaddingTop() + getPaddingBottom();
	}

	public int getExpandedHeight() {
		return expandedHeight;
	}

	public void collapse() {
		if (isExpanded()) {
			expanded = false;
			Log.v(TAG, "collapse()");
			requestLayout();
		}
	}

	public void expand() {
		if (!isExpanded()) {
			Log.v(TAG, "expaned()");
			expanded = true;
			requestLayout();
		}
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void toggle() {
		if (isExpanded()) {
			collapse();
		} else {
			expand();
		}
	}

	public void setTagsMeasuredCallBack(TagsMeasuredCallBack tagsMeasuredCallBack) {
		this.tagsMeasuredCallBack = tagsMeasuredCallBack;
	}

	public void setmOnTagItemClicktener(OnTagItemClickListener mOnTagItemClicktener) {
		this.mOnTagItemClicktener = mOnTagItemClicktener;
	}

	public void setTags(List<TagItem> tags) {
		this.tags = tags;
		if (null == this.tags) {
			this.tags = new ArrayList<TagItem>();
		}
	}

	public int getTagsSize() {
		return null == tags ? 0 : tags.size();
	}

	public void layoutTags() {
		removeAllViewsInLayout();
		currentWidth = 0;
		row = 0;
		RelativeLayout rowRel = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.tags_row, null);
		LinearLayout linear = (LinearLayout) rowRel.findViewById(R.id.tag_linear);
		int tagSize = getTagsSize();
		if (tagSize == 0) {
			return;
		}
		logTags();
		for (int i = 0; i < tagSize; i++) {
			final int position = i;
			final TagItem item = tags.get(i);
			final String tag = item.mText;
			View itemView = LayoutInflater.from(getContext()).inflate(itemLayoutId, null);
			final TextView textView = (TextView) itemView.findViewById(textViewId);
			textView.setText(tag);
			textView.setOnClickListener(new OnClickListener() {

				@SuppressLint("NewApi")
				@Override
				public void onClick(View v) {
					if (textView.isSelected()) {
						textView.setSelected(false);
						if (null != mOnTagItemClicktener) {
							mOnTagItemClicktener.onTagItemClick(position, false, item);
						}
					} else {
						textView.setSelected(true);
						if (null != mOnTagItemClicktener) {
							mOnTagItemClicktener.onTagItemClick(position, true, item);
						}
					}
				}
			});
			if (item.row == row) {
				linear.addView(itemView);
			} else {
				//当被折叠时只布局前collpasedRowsNum行
				LayoutParams params = (LayoutParams) rowRel.getLayoutParams();
				if (params == null) {
					params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				}
				addViewInLayout(rowRel, -1, params);
				rowRel.measure(MeasureSpec.EXACTLY | rowWidth, MeasureSpec.UNSPECIFIED);
				rowRel.layout(getPaddingLeft(), getPaddingTop() + row * rowHeight, rowWidth + getPaddingLeft(), row * rowHeight + rowHeight + getPaddingTop());
				int nextRow = row + 1;
				if (!isExpanded() && item.row > collpasedRowsNum || nextRow * rowHeight >= getHeight() || limitedRowNum != -1 && nextRow >= limitedRowNum) {
					break;
				}
				rowRel = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.tags_row, null);
				linear = (LinearLayout) rowRel.findViewById(R.id.tag_linear);
				linear.addView(itemView);
				row++;
				Log.v(TAG, "row: " + row);
			}
			if (i == tagSize - 1) {
				LayoutParams params = (LayoutParams) rowRel.getLayoutParams();
				if (params == null) {
					params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				}
				addViewInLayout(rowRel, -1, params);
				rowRel.measure(MeasureSpec.EXACTLY | rowWidth, MeasureSpec.UNSPECIFIED);
				rowRel.layout(getPaddingLeft(), getPaddingTop() + row * rowHeight, rowWidth + getPaddingLeft(), row * rowHeight + rowHeight + getPaddingTop());
			}
		}
	}

	private void logTags() {
		if (!BuildConfig.DEBUG) {
			return;
		}
		int size = (null != tags && tags.size() > 0) ? tags.size() : 0;
		if (size == 0) {
			return;
		}
		int maxRow = 0;
		StringBuilder sb = new StringBuilder("[");
		for (int i=0; i<tags.size(); i++) {
			sb.append(tags.get(i).mText);
			if (i == tags.size() - 1) {
				sb.append("]");
				maxRow = tags.get(i).row;
			} else {
				sb.append(", ");
			}
		}
		Log.v(TAG, "tags(" + position + "): " + size + "  " + sb.toString());
		Log.v(TAG, "max row = " + maxRow);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
//		Log.v(TAG, "onLayout");
		layoutTags();
//		positioinItems();
		invalidate();
	}


	private void positioinItems() {
		int top = getPaddingTop();

		for (int i = 0; i < getChildCount(); i++) {
			final View child = getChildAt(i);

			final int width = child.getMeasuredWidth();
			final int height = child.getMeasuredHeight();
			final int left = (getWidth() - width) / 2;

			child.layout(left, top, left + width, top + height);
			top += height;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		Log.v(TAG, "onMeasure");
		int width = 0;
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {
			width = specSize;
		} else if (specMode == MeasureSpec.AT_MOST) {
			width = specSize;
		}
		rowWidth = width - getPaddingLeft() - getPaddingRight();
		int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);
		if (hSpecMode == MeasureSpec.EXACTLY) {
			viewHeight = hSpecSize;
			setMeasuredDimension(width, hSpecSize);
			needExpanded = false;
		} else {
			expandedHeight = measureHeight();
			if (expandedHeight <= getCollapsedHeight() || limitedRowNum != -1) {
				needExpanded = false;
				if (tagsMeasuredCallBack != null) {
					tagsMeasuredCallBack.tagsNeedlessExpanded(getPosition(), getExpandedHeight());
				}
			} else {
				needExpanded = true;
				if (tagsMeasuredCallBack != null) {
					tagsMeasuredCallBack.tagsNeedExpanded(getPosition(), getCollapsedHeight(), getExpandedHeight());
				}
			}
			if (needExpanded && !isExpanded()) {
				viewHeight = getCollapsedHeight();
			} else {
				viewHeight = getExpandedHeight();
			}
//			Log.v(TAG, "viewHeight: " + viewHeight);
			setMeasuredDimension(width, viewHeight);
		}

	}

	/**
	 * Measure 当前tags的ViewHeight，是展开时最大的height <br>
	 *     并确定每一个item所在的行号
	 * @return
	 */
	private int measureHeight() {
		currentWidth = 0;
		int height = 0;
		row = 0;
		View itemView = LayoutInflater.from(getContext()).inflate(itemLayoutId, null);
		final TextView textView = (TextView) itemView.findViewById(textViewId);
		int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		if (rowHeight <= 0) {   //只有当没有行高的时候才measure一次，取得行高
			itemView.measure(w, h);
			rowHeight = itemView.getMeasuredHeight();
		}
		if (tags == null || tags.size() == 0) {
			rowsNum = 0;
			return 0;
		} else {
			rowsNum = 1;
			height += rowHeight;
		}
		for (int i = 0; i < tags.size(); i++) {
			TagItem tag = tags.get(i);
			textView.setText(tag.mText);
			int measureWidth = 0;
			int measureHeight = rowHeight;
			// 如果有测量过该字符串则直接使用测量过的高度
			if (cacheWidthMap.containsKey(tag.mText)) {
				measureWidth = cacheWidthMap.get(tag.mText);
			} else {
				itemView.measure(w, h);
				measureWidth = itemView.getMeasuredWidth();
				cacheWidthMap.put(tag.mText, measureWidth);
			}
			if (currentWidth + measureWidth <= rowWidth) {
				currentWidth += measureWidth;
				tag.row = row;
			} else if (currentWidth + measureWidth > rowWidth) {
				if (limitedRowNum != -1 && row == limitedRowNum - 1) {
					break;
				}
				// 如果currentWidth＝＝0说明当前的item长度已经超过一行的长度，则将其放在当前行
				if (currentWidth == 0) {
					tag.row = row;
					if (i != tags.size() - 1) {
						height += measureHeight;
						currentWidth = 0;
						row++;
					}
				} else {
					height += measureHeight;
					currentWidth = 0;
					row++;
					currentWidth += measureWidth;
					tag.row = row;
				}
			}
		}
		rowsNum = row + 1;
		row = 0;
		return height + getPaddingTop() + getPaddingBottom();
	}


	public interface OnTagItemClickListener {
		public void onTagItemClick(int position, boolean isSelected, TagItem tag);
	}

	/**
	 * 在meaure时回调该View是否需要折叠
	 */
	public interface TagsMeasuredCallBack {
		public void tagsNeedExpanded(int position, int collapsedHeight, int expandedHeight);

		public void tagsNeedlessExpanded(int position, int height);
	}
}
