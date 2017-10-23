package org.d3ifcool.dailyactivity;

/**
 * Created by aldiand on 10/4/17.
 */

public class Jadwal {
    private String mJam;
    private String mSubject;

	public static final String EXTRA_HARI = "extra_hari";
	public static final String MINGGU = "minggu";
	public static final String SENIN = "senin";
	public static final String SELASA = "selasa";
	public static final String RABU = "rabu";
	public static final String KAMIS = "kamis";
	public static final String JUMAT = "jumat";
	public static final String SABTU = "sabtu";

	public Jadwal(){

	}
	public Jadwal(String mJam, String mSubject) {
		this.mSubject = mSubject;
		this.mJam = mJam;
	}

    public String getJam() {
        return mJam;
    }

    public void setJam(String mJam) {
        this.mJam = mJam;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String mSubject) {
        this.mSubject = mSubject;
    }
}
