/*
 * Copyright 2024 Erich Steiger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.erichsteiger.eopt.bo;

public class Version implements Comparable<Version> {

	private String version;

	public Version() {

	}

	public Version(String version) {
		this.setVersion(version);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public int compareTo(Version o) {
		String[] mySplits = version.split("\\.");
		String[] otherSplits = o.getVersion().split("\\.");
		for (int i = 0; i < mySplits.length; i++) {
			if (otherSplits.length - 1 < (i)) {
				return -1;
			}
			if (Long.valueOf(mySplits[i]).compareTo(Long.valueOf(otherSplits[i])) < 0) {
				return -1;
			}
			if (Long.valueOf(mySplits[i]).compareTo(Long.valueOf(otherSplits[i])) > 0) {
				return 1;
			}
		}
		return version.compareTo(o.getVersion());
	}

	@Override
	public String toString() {
		return version == null ? "" : version;
	}

}
