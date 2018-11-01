package cn.pumch.web.model;

import java.util.Date;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Operator {
    private String id;

    private String nickName;
    @NotEmpty(message="密码不能为空")
    @Length(min=6,max=16,message="密码长度不正确，得在6-16之间")
    private String password;

    private String positionDescreption;
    @NotEmpty(message = "手机号码不能为空")
    private String tel;
    @Email(message="邮箱格式不正确")
    private String mail;

    private String city;

    private String address;

    //是否已填写企业信息
    private boolean enterprise = true;

    private Date regDate;

    private String practice;

    private String companyName;

    private String mainCompanyName;

    private String industry;

    private String leadOffice;

    private String headquartersOffice;

    private String keyAccountManager;

    private String titleOfEngagement;

    private String typeOfPartnership;

    private String statusOfEngagement;

    private String nationalOrInternationalEngagement;

    private String startDateOfEngagement;

    private String endDateOfEngagement;

    private String descriptionOfPartnership;

    private String conservationFocusOfEngagement;

    private Integer budgetRangeInEur;

    private Boolean isRestrictedEngagement;

    private Boolean isCorporateFoudation;

    private String contactPerson;

    private int isChecked;

    private String verifyCode;

    private long verifyGenerateTimestamp;


    public Operator() {}

    public Operator(String id, String nickName, String password, String positionDescreption, String tel, String mail, String city, String address, boolean enterprise, Date regDate, String practice, String companyName, String mainCompanyName, String industry, String leadOffice, String headquartersOffice, String keyAccountManager, String titleOfEngagement, String typeOfPartnership, String statusOfEngagement, String nationalOrInternationalEngagement, String startDateOfEngagement, String endDateOfEngagement, String descriptionOfPartnership, String conservationFocusOfEngagement, Integer budgetRangeInEur, Boolean isRestrictedEngagement, Boolean isCorporateFoudation, String contactPerson, int isChecked, String verifyCode, long verifyGenerateTimestamp) {
        this.id = id;
        this.nickName = nickName;
        this.password = password;
        this.positionDescreption = positionDescreption;
        this.tel = tel;
        this.mail = mail;
        this.city = city;
        this.address = address;
        this.enterprise = enterprise;
        this.regDate = regDate;
        this.practice = practice;
        this.companyName = companyName;
        this.mainCompanyName = mainCompanyName;
        this.industry = industry;
        this.leadOffice = leadOffice;
        this.headquartersOffice = headquartersOffice;
        this.keyAccountManager = keyAccountManager;
        this.titleOfEngagement = titleOfEngagement;
        this.typeOfPartnership = typeOfPartnership;
        this.statusOfEngagement = statusOfEngagement;
        this.nationalOrInternationalEngagement = nationalOrInternationalEngagement;
        this.startDateOfEngagement = startDateOfEngagement;
        this.endDateOfEngagement = endDateOfEngagement;
        this.descriptionOfPartnership = descriptionOfPartnership;
        this.conservationFocusOfEngagement = conservationFocusOfEngagement;
        this.budgetRangeInEur = budgetRangeInEur;
        this.isRestrictedEngagement = isRestrictedEngagement;
        this.isCorporateFoudation = isCorporateFoudation;
        this.contactPerson = contactPerson;
        this.isChecked = isChecked;
        this.verifyCode = verifyCode;
        this.verifyGenerateTimestamp = verifyGenerateTimestamp;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPositionDescreption() {
        return positionDescreption;
    }

    public void setPositionDescreption(String positionDescreption) {
        this.positionDescreption = positionDescreption;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isEnterprise() {
        return enterprise;
    }

    public void setEnterprise(boolean enterprise) {
        this.enterprise = enterprise;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMainCompanyName() {
        return mainCompanyName;
    }

    public void setMainCompanyName(String mainCompanyName) {
        this.mainCompanyName = mainCompanyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getLeadOffice() {
        return leadOffice;
    }

    public void setLeadOffice(String leadOffice) {
        this.leadOffice = leadOffice;
    }

    public String getHeadquartersOffice() {
        return headquartersOffice;
    }

    public void setHeadquartersOffice(String headquartersOffice) {
        this.headquartersOffice = headquartersOffice;
    }

    public String getKeyAccountManager() {
        return keyAccountManager;
    }

    public void setKeyAccountManager(String keyAccountManager) {
        this.keyAccountManager = keyAccountManager;
    }

    public String getTitleOfEngagement() {
        return titleOfEngagement;
    }

    public void setTitleOfEngagement(String titleOfEngagement) {
        this.titleOfEngagement = titleOfEngagement;
    }

    public String getTypeOfPartnership() {
        return typeOfPartnership;
    }

    public void setTypeOfPartnership(String typeOfPartnership) {
        this.typeOfPartnership = typeOfPartnership;
    }

    public String getStatusOfEngagement() {
        return statusOfEngagement;
    }

    public void setStatusOfEngagement(String statusOfEngagement) {
        this.statusOfEngagement = statusOfEngagement;
    }

    public String getNationalOrInternationalEngagement() {
        return nationalOrInternationalEngagement;
    }

    public void setNationalOrInternationalEngagement(String nationalOrInternationalEngagement) {
        this.nationalOrInternationalEngagement = nationalOrInternationalEngagement;
    }

    public String getStartDateOfEngagement() {
        return startDateOfEngagement;
    }

    public void setStartDateOfEngagement(String startDateOfEngagement) {
        this.startDateOfEngagement = startDateOfEngagement;
    }

    public String getEndDateOfEngagement() {
        return endDateOfEngagement;
    }

    public void setEndDateOfEngagement(String endDateOfEngagement) {
        this.endDateOfEngagement = endDateOfEngagement;
    }

    public String getDescriptionOfPartnership() {
        return descriptionOfPartnership;
    }

    public void setDescriptionOfPartnership(String descriptionOfPartnership) {
        this.descriptionOfPartnership = descriptionOfPartnership;
    }

    public String getConservationFocusOfEngagement() {
        return conservationFocusOfEngagement;
    }

    public void setConservationFocusOfEngagement(String conservationFocusOfEngagement) {
        this.conservationFocusOfEngagement = conservationFocusOfEngagement;
    }

    public Integer getBudgetRangeInEur() {
        return budgetRangeInEur;
    }

    public void setBudgetRangeInEur(Integer budgetRangeInEur) {
        this.budgetRangeInEur = budgetRangeInEur;
    }

    public Boolean getRestrictedEngagement() {
        return isRestrictedEngagement;
    }

    public void setRestrictedEngagement(Boolean restrictedEngagement) {
        isRestrictedEngagement = restrictedEngagement;
    }

    public Boolean getCorporateFoudation() {
        return isCorporateFoudation;
    }

    public void setCorporateFoudation(Boolean corporateFoudation) {
        isCorporateFoudation = corporateFoudation;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public long getVerifyGenerateTimestamp() {
        return verifyGenerateTimestamp;
    }

    public void setVerifyGenerateTimestamp(long verifyGenerateTimestamp) {
        this.verifyGenerateTimestamp = verifyGenerateTimestamp;
    }


    @Override
    public String toString() {
        return "Operator{" +
                "id='" + id + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", positionDescreption='" + positionDescreption + '\'' +
                ", tel='" + tel + '\'' +
                ", mail='" + mail + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", enterprise=" + enterprise +
                ", regDate=" + regDate +
                ", practice='" + practice + '\'' +
                ", companyName='" + companyName + '\'' +
                ", mainCompanyName='" + mainCompanyName + '\'' +
                ", industry='" + industry + '\'' +
                ", leadOffice='" + leadOffice + '\'' +
                ", headquartersOffice='" + headquartersOffice + '\'' +
                ", keyAccountManager='" + keyAccountManager + '\'' +
                ", titleOfEngagement='" + titleOfEngagement + '\'' +
                ", typeOfPartnership='" + typeOfPartnership + '\'' +
                ", statusOfEngagement='" + statusOfEngagement + '\'' +
                ", nationalOrInternationalEngagement='" + nationalOrInternationalEngagement + '\'' +
                ", startDateOfEngagement='" + startDateOfEngagement + '\'' +
                ", endDateOfEngagement='" + endDateOfEngagement + '\'' +
                ", descriptionOfPartnership='" + descriptionOfPartnership + '\'' +
                ", conservationFocusOfEngagement='" + conservationFocusOfEngagement + '\'' +
                ", budgetRangeInEur=" + budgetRangeInEur +
                ", isRestrictedEngagement=" + isRestrictedEngagement +
                ", isCorporateFoudation=" + isCorporateFoudation +
                ", contactPerson='" + contactPerson + '\'' +
                ", isChecked=" + isChecked +
                ", verifyCode='" + verifyCode + '\'' +
                ", verifyGenerateTimestamp=" + verifyGenerateTimestamp +
                '}';
    }
}