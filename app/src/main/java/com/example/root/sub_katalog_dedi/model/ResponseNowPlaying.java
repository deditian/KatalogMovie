package com.example.root.sub_katalog_dedi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ResponseNowPlaying implements Parcelable{
	private Dates dates;
	private int page;
	private int totalPages;
	private List<ResultsItem> results;
	private int totalResults;

	protected ResponseNowPlaying(Parcel in) {
		page = in.readInt();
		totalPages = in.readInt();
		results = in.createTypedArrayList(ResultsItem.CREATOR);
		totalResults = in.readInt();
	}

	public static final Creator<ResponseNowPlaying> CREATOR = new Creator<ResponseNowPlaying>() {
		@Override
		public ResponseNowPlaying createFromParcel(Parcel in) {
			return new ResponseNowPlaying(in);
		}

		@Override
		public ResponseNowPlaying[] newArray(int size) {
			return new ResponseNowPlaying[size];
		}
	};

	public void setDates(Dates dates){
		this.dates = dates;
	}

	public Dates getDates(){
		return dates;
	}

	public void setPage(int page){
		this.page = page;
	}

	public int getPage(){
		return page;
	}

	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	public int getTotalResults(){
		return totalResults;
	}

	@Override
 	public String toString(){
		return 
			"ResponseNowPlaying{" + 
			"dates = '" + dates + '\'' + 
			",page = '" + page + '\'' + 
			",total_pages = '" + totalPages + '\'' + 
			",results = '" + results + '\'' + 
			",total_results = '" + totalResults + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(page);
		dest.writeInt(totalPages);
		dest.writeTypedList(results);
		dest.writeInt(totalResults);
	}
}