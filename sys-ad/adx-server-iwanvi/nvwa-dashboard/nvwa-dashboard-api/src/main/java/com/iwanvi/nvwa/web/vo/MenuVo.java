/*
 * Copyright 2017 The OpenDSP Project
 *
 * The OpenDSP Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.iwanvi.nvwa.web.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author wangwp
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuVo {
	@JsonProperty("title")
	private String name;
	@JsonProperty("index")
	private String path;
	private Integer readonly;
	@JsonProperty("icon")
	private String icon;

	@JsonProperty("subs")
	private List<MenuVo> childs;

	public List<MenuVo> getChilds() {
		return childs;
	}

	public void setChilds(List<MenuVo> childs) {
		this.childs = childs;
		if (childs == null || childs.isEmpty()) {
			this.childs = null;
		}
	}

	public String getName() {
		return name;
	}

	public Integer getReadonly() {
		return readonly;
	}

	public void setReadonly(Integer readonly) {
		this.readonly = readonly;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
