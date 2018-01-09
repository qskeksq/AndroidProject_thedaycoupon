package com.thedaycoupon.domain.favorite;

/**
 * Created by Administrator on 2017-12-05.
 */

public class FavoriteInfo {

    private RESULT RESULT;

    private String totalCount;

    private Favorite[] favorite;

    public RESULT getRESULT ()
    {
        return RESULT;
    }

    public void setRESULT (RESULT RESULT)
    {
        this.RESULT = RESULT;
    }

    public String getTotalCount ()
    {
        return totalCount;
    }

    public void setTotalCount (String totalCount)
    {
        this.totalCount = totalCount;
    }

    public Favorite[] getFavorite ()
    {
        return favorite;
    }

    public void setFavorite (Favorite[] favorite)
    {
        this.favorite = favorite;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [RESULT = "+RESULT+", totalCount = "+totalCount+", favorite = "+favorite+"]";
    }

}
