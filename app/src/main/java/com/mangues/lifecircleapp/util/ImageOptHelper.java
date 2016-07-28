package com.mangues.lifecircleapp.util;

import android.graphics.Bitmap;

import com.mangues.lifecircleapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ImageOptHelper {

	/**
	 * 默认DisplayImageOptions
	 */
	public static DisplayImageOptions getDefaultOptions() {
		return getCommonImageOptions(R.mipmap.ic_launcher, 0);
	}
//
//	/**
//	 * 头像DisplayImageOptions，使用者必须为ImageAware对象
//	 */
//	public static DisplayImageOptions getAvatarOptions() {
//		// 圆角最大不超过半径，即为圆形
//		return getCommonImageOptions(R.mipmap.avatar_default, 999);
//	}

	/**
	 *
	 * 通用DisplayImageOptions
	 *
	 * @param imgRes 空地址/加载中/加载失败使用图片
	 * @param radius 图片圆角半径,ImageAware对象才能使用
	 *
	 * @return DisplayImageOptions
	 */
	public static DisplayImageOptions getCommonImageOptions(int imgRes, int radius) {
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
				.cacheOnDisk(true)
				.cacheInMemory(true)
				.bitmapConfig(Bitmap.Config.RGB_565);

		if(imgRes != -1) {
			builder.showImageForEmptyUri(imgRes)
					.showImageOnFail(imgRes)
					.showImageOnLoading(imgRes);
		}

		if(radius > 0) {
			builder.displayer(new RoundedBitmapDisplayer(radius));
		}

		return builder.build();
	}
	
}
