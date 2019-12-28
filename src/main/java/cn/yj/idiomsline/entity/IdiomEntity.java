package cn.yj.idiomsline.entity;


import javax.persistence.*;

@Entity
@Table(name="t_idiom")
public class IdiomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String value;
    private String wordS;
    private String wordE;
    private String pinyinS;
    private String pinyinE;
    private String pinyin;
    private String paraphrase;
    private String provenance;
    private String example;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPinyinS() {
        return pinyinS;
    }

    public void setPinyinS(String pinyinS) {
        this.pinyinS = pinyinS;
    }

    public String getPinyinE() {
        return pinyinE;
    }

    public void setPinyinE(String pinyinE) {
        this.pinyinE = pinyinE;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getParaphrase() {
        return paraphrase;
    }

    public void setParaphrase(String paraphrase) {
        this.paraphrase = paraphrase;
    }

    public String getProvenance() {
        return provenance;
    }

    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWordS() {
        return wordS;
    }

    public void setWordS(String wordS) {
        this.wordS = wordS;
    }

    public String getWordE() {
        return wordE;
    }

    public void setWordE(String wordE) {
        this.wordE = wordE;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @Override
    public String toString() {
        return "IdiomEntity{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", wordS='" + wordS + '\'' +
                ", wordE='" + wordE + '\'' +
                ", pinyinS='" + pinyinS + '\'' +
                ", pinyinE='" + pinyinE + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", paraphrase='" + paraphrase + '\'' +
                ", provenance='" + provenance + '\'' +
                ", example='" + example + '\'' +
                '}';
    }
}
