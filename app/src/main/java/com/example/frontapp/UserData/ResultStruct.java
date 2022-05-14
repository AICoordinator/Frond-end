package com.example.frontapp.UserData;

public class ResultStruct {
    private byte[] originBytearr;
    private byte[] changedBytearr;
    private String resultScore;

    public byte[] getOriginBytearr() {
        return originBytearr;
    }

    public void setOriginBytearr(byte[] originBytearr) {
        this.originBytearr = originBytearr;
    }

    public byte[] getChangedBytearr() {
        return changedBytearr;
    }

    public void setChangedBytearr(byte[] changedBytearr) {
        this.changedBytearr = changedBytearr;
    }

    public String getResultScore() {
        return resultScore;
    }

    public void setResultScore(String resultScore) {
        this.resultScore = resultScore;
    }

    public ResultStruct(byte[] originBytearr, byte[] changedBytearr, String resultScore) {
        this.originBytearr = originBytearr;
        this.changedBytearr = changedBytearr;
        this.resultScore = resultScore;
    }
}
