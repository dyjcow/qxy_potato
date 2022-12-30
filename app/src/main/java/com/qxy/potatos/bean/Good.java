package com.qxy.potatos.bean;

/**
 * @author ：Dyj
 * @date ：Created in 2022/12/30 7:37
 * @description：物品项
 * @modified By：
 * @version: 1.0
 */
public class Good {
	private int gno;
	private String gname;
	private String gdescribe;
	private String gcolour;
	private String gcategory;
	private int lno;
	private String uno;

	public int getGno() {
		return gno;
	}

	public void setGno(int gno) {
		this.gno = gno;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getGdescribe() {
		return gdescribe;
	}

	public void setGdescribe(String gdescribe) {
		this.gdescribe = gdescribe;
	}

	public String getGcolour() {
		return gcolour;
	}

	public void setGcolour(String gcolour) {
		this.gcolour = gcolour;
	}

	public String getGcategory() {
		return gcategory;
	}

	public void setGcategory(String gcategory) {
		this.gcategory = gcategory;
	}

	public int getLno() {
		return lno;
	}

	public void setLno(int lno) {
		this.lno = lno;
	}

	public String getUno() {
		return uno;
	}

	public void setUno(String uno) {
		this.uno = uno;
	}
}
