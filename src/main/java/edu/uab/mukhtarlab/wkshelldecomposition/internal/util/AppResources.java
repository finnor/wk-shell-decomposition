package edu.uab.mukhtarlab.wkshelldecomposition.internal.util;

import java.net.URL;

public class AppResources {

	public static enum ImageName {
		LOGO("/img/logo.png");


		private final String name;

		private ImageName(final String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public static URL getUrl(ImageName img) {
		return AppResources.class.getResource(img.toString());
	}
}
