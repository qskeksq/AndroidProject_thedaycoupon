package com.thedaycoupon.domain.member;

/**
 * Created by Administrator on 2017-11-30.
 */
public class MemberInfo {

    private Member[] member;

    private RESULT RESULT;

    private String totalCount;

    public Member[] getMember ()
    {
        return member;
    }

    public void setMember (Member[] member)
    {
        this.member = member;
    }

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

    @Override
    public String toString()
    {
        return "ClassPojo [member = "+member+", RESULT = "+RESULT+", totalCount = "+totalCount+"]";
    }

}
