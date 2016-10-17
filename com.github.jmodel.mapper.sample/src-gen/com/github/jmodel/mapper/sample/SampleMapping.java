package com.github.jmodel.mapper.sample;

import com.github.jmodel.mapper.api.Mapping;
import com.github.jmodel.mapper.api.Model;

@SuppressWarnings("all")
public class SampleMapping extends Mapping {
  private static Mapping instance;
  
  public static synchronized Mapping getInstance() {
    if (instance == null) {
    	instance = new com.github.jmodel.mapper.sample.SampleMapping();
    	instance.setFromFormat(com.github.jmodel.mapper.api.FormatEnum.JSON);														
    	
    	instance.setToFormat(com.github.jmodel.mapper.api.FormatEnum.JSON);														
    	
    	com.github.jmodel.mapper.api.Entity sourceRootModel = new com.github.jmodel.mapper.impl.EntityImpl();
    	instance.setSourceTemplateModel(sourceRootModel);
    	com.github.jmodel.mapper.api.Entity targetRootModel = new com.github.jmodel.mapper.impl.EntityImpl();
    	instance.setTargetTemplateModel(targetRootModel); 	
    				
    		
    		instance.getRawSourceFieldPaths().add("Policy._");
    		instance.getRawTargetFieldPaths().add("PolicyEntity._");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[]._");
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[]._");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyContactList[]._");
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[]._");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[]._");
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[]._");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg._");
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[]._");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyContactList[]._");
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[]._");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[]._");
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[]._");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[]._");
    		instance.getRawTargetFieldPaths().add("PolicyEntity._");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[]._");
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[]._");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[]._");
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].PolicyLimit._");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[]._");
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].PolicyLimit.PolicyLimitValue[]._");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[]._");
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].CoverageList[]._");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyCoverageList[]._");
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].CoverageList[]._");
    	
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyType");
    		instance.getRawSourceFieldPaths().add("Policy.Type");
    		instance.getRawSourceFieldPaths().add("Policy.EffDate");
    		instance.getRawSourceFieldPaths().add("Policy.ExpDate");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyContactList[].IsPrimaryContact");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyContactList[].ContactName");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].IsPrimaryAddress");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].ContactAddress");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyName");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyContactList[].IsPrimaryContact");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyContactList[].ContactName");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].IsPrimaryAddress");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].ContactAddress");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].ProductCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].CountryCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].InsuredCategory");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].District");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Address");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].InsuredName");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].SumInsured");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].IsAllowance");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].LimitValue");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].ModeType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].ValueType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].PolicyRiskNo");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].ProductCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyCoverageList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyCoverageList[].ProductCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyType");
    		instance.getRawSourceFieldPaths().add("Policy.Type");
    		instance.getRawSourceFieldPaths().add("Policy.EffDate");
    		instance.getRawSourceFieldPaths().add("Policy.ExpDate");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyContactList[].IsPrimaryContact");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyContactList[].ContactName");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].IsPrimaryAddress");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].ContactAddress");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyName");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyContactList[].IsPrimaryContact");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyContactList[].ContactName");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].IsPrimaryAddress");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].ContactAddress");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].ProductCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].CountryCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].InsuredCategory");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].District");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Address");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].InsuredName");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].SumInsured");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].IsAllowance");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].LimitValue");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].ModeType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].ValueType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].PolicyRiskNo");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].ProductCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyCoverageList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyCoverageList[].ProductCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyType");
    		instance.getRawSourceFieldPaths().add("Policy.Type");
    		instance.getRawSourceFieldPaths().add("Policy.EffDate");
    		instance.getRawSourceFieldPaths().add("Policy.ExpDate");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyContactList[].IsPrimaryContact");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyContactList[].ContactName");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].IsPrimaryAddress");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].ContactAddress");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyName");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyContactList[].IsPrimaryContact");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyContactList[].ContactName");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].IsPrimaryAddress");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].ContactAddress");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].ProductCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].CountryCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].InsuredCategory");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].District");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Address");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].InsuredName");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].SumInsured");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].IsAllowance");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].LimitValue");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].ModeType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].ValueType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].PolicyRiskNo");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].ProductCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyCoverageList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyCoverageList[].ProductCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyType");
    		instance.getRawSourceFieldPaths().add("Policy.Type");
    		instance.getRawSourceFieldPaths().add("Policy.EffDate");
    		instance.getRawSourceFieldPaths().add("Policy.ExpDate");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyContactList[].IsPrimaryContact");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyContactList[].ContactName");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].IsPrimaryAddress");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].ContactAddress");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyName");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyContactList[].IsPrimaryContact");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyContactList[].ContactName");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].IsPrimaryAddress");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].ContactAddress");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].ProductCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].CountryCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].InsuredCategory");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].District");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Address");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].InsuredName");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].SumInsured");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].IsAllowance");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].LimitValue");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].ModeType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].ValueType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].PolicyRiskNo");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].ProductCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyCoverageList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyCoverageList[].ProductCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyType");
    		instance.getRawSourceFieldPaths().add("Policy.Type");
    		instance.getRawSourceFieldPaths().add("Policy.EffDate");
    		instance.getRawSourceFieldPaths().add("Policy.ExpDate");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyContactList[].IsPrimaryContact");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyContactList[].ContactName");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].IsPrimaryAddress");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].ContactAddress");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyName");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyContactList[].IsPrimaryContact");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyContactList[].ContactName");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].IsPrimaryAddress");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].ContactAddress");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].ProductCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].CountryCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].InsuredCategory");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].District");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Address");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].InsuredName");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].SumInsured");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].IsAllowance");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].LimitValue");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].ModeType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].ValueType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].PolicyRiskNo");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].PolicyCoverageList[].ProductCode");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyCoverageList[].Type");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyCoverageList[].ProductCode");
    		
    		instance.getRawSourceFieldPaths().add("Policy.EffectiveDate");
    		instance.getRawSourceFieldPaths().add("Policy.ExpiryDate");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyContactList[].Name");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyName");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyContactList[].Name");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.SalesChannelOrg.PartyAddressList[].AddressLine1");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].ProductId");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Country");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Province");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].City");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].District");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].Address");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].SumInsured");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].PolicyRiskList[].InsuredId");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].ProductId");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyLobList[].ProductId");
    		
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].ContactType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyType");
    		instance.getRawSourceFieldPaths().add("Policy.PolicyCustomerList[].Customer.PartyType");
    		
    		instance.getRawTargetFieldPaths().add("PolicyEntity.Type");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.EffDate");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.ExpDate");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[].ContactType");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[].ContactType");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[].ContactType");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[].ContactName");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[].ContactAddress");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[].ContactType");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[].PartyName");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[].ContactName");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.PartyList[].ContactAddress");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.ProductCode");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].Type");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].CountryCode");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].InsuredCategory");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].InsuredName");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].PolicyLimit.Type");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].PolicyLimit.PolicyLimitValue[].Type");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].PolicyLimit.PolicyLimitValue[].IsAllowance");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].PolicyLimit.PolicyLimitValue[].LimitValue");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].PolicyLimit.PolicyLimitValue[].ModeType");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].PolicyLimit.PolicyLimitValue[].ValueType");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].CoverageList[].Type");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].CoverageList[].PolicyRiskNo");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].CoverageList[].ProductCode");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].CoverageList[].Type");												
    		instance.getRawTargetFieldPaths().add("PolicyEntity.InsuredList[].CoverageList[].ProductCode");												
    }	
    
    return instance;
    
  }
  
  @Override
  public void execute(final Model mySourceModel, final Model myTargetModel) {
    {
    {
    String fieldValue = null;
    fieldValue = "PolicyType";
    myTargetModel.getFieldPathMap().get("PolicyEntity.Type").setValue(fieldValue); 
    
    }
    {
    String fieldValue = null;
    fieldValue = mySourceModel.getFieldPathMap().get("Policy.EffectiveDate").getValue();
    myTargetModel.getFieldPathMap().get("PolicyEntity.EffDate").setValue(fieldValue); 
    
    }
    {
    String fieldValue = null;
    fieldValue = mySourceModel.getFieldPathMap().get("Policy.ExpiryDate").getValue();
    myTargetModel.getFieldPathMap().get("PolicyEntity.ExpDate").setValue(fieldValue); 
    
    }
    {
    java.util.function.Predicate<String> p = null;
    com.github.jmodel.mapper.api.ModelHelper.arrayMapping(mySourceModel, myTargetModel, mySourceModel.getModelPathMap().get("Policy.PolicyCustomerList[]"), myTargetModel.getModelPathMap().get("PolicyEntity.PartyList[]"), "Policy.PolicyCustomerList[]", "PolicyEntity.PartyList[]", 0, false, p,
    (String[] m_1) ->
    {
    {
    if ((mySourceModel.getFieldPathMap().get(m_1[0] + ".Customer.PartyType").getValue().equals("20"))) {
      {
      String fieldValue = null;
      fieldValue = "1";
      myTargetModel.getFieldPathMap().get(m_1[1] + ".ContactType").setValue(fieldValue); 
      
      }
    } else {
      if ((mySourceModel.getFieldPathMap().get(m_1[0] + ".Customer.PartyType").getValue().equals("21"))) {
        {
        String fieldValue = null;
        fieldValue = "2";
        myTargetModel.getFieldPathMap().get(m_1[1] + ".ContactType").setValue(fieldValue); 
        
        }
      } else {
        {
        String fieldValue = null;
        fieldValue = mySourceModel.getFieldPathMap().get(m_1[0] + ".Customer.PartyType").getValue();
        myTargetModel.getFieldPathMap().get(m_1[1] + ".ContactType").setValue(fieldValue); 
        
        }
      }
    }
    }
    {
    java.util.function.Predicate<String> p_1 = null;
    p_1 = (String f) -> (mySourceModel.getFieldPathMap().get(f + ".IsPrimaryContact").getValue().equals("Y"));
    com.github.jmodel.mapper.api.ModelHelper.arrayMapping(mySourceModel, myTargetModel, mySourceModel.getModelPathMap().get(m_1[0] + ".Customer.PartyContactList[]"), myTargetModel.getModelPathMap().get(m_1[1]), m_1[0] + ".Customer.PartyContactList[]", m_1[1], Integer.valueOf(m_1[2]), false, p_1,
    (String[] m_2) ->
    {
    {
    String fieldValue = null;
    fieldValue = mySourceModel.getFieldPathMap().get(m_2[0] + ".Name").getValue();
    myTargetModel.getFieldPathMap().get(m_2[1] + ".ContactName").setValue(fieldValue); 
    
    }
    });
    }
    {
    java.util.function.Predicate<String> p_2 = null;
    p_2 = (String f_1) -> (mySourceModel.getFieldPathMap().get(f_1 + ".IsPrimaryAddress").getValue().equals("Y"));
    com.github.jmodel.mapper.api.ModelHelper.arrayMapping(mySourceModel, myTargetModel, mySourceModel.getModelPathMap().get(m_1[0] + ".Customer.PartyAddressList[]"), myTargetModel.getModelPathMap().get(m_1[1]), m_1[0] + ".Customer.PartyAddressList[]", m_1[1], Integer.valueOf(m_1[2]), false, p_2,
    (String[] m_3) ->
    {
    {
    String fieldValue = null;
    fieldValue = ((((((((mySourceModel.getFieldPathMap().get(m_3[0] + ".Country").getValue()+";")+mySourceModel.getFieldPathMap().get(m_3[0] + ".Province").getValue())+";")+mySourceModel.getFieldPathMap().get(m_3[0] + ".City").getValue())+";")+mySourceModel.getFieldPathMap().get(m_3[0] + ".AddressLine1").getValue())+";")+mySourceModel.getFieldPathMap().get(m_3[0] + ".AddressLine1").getValue());
    myTargetModel.getFieldPathMap().get(m_3[1] + ".ContactAddress").setValue(fieldValue); 
    
    }
    });
    }
    });
    }
    {
    java.util.function.Predicate<String> p_3 = null;
    com.github.jmodel.mapper.api.ModelHelper.arrayMapping(mySourceModel, myTargetModel, mySourceModel.getModelPathMap().get("Policy.SalesChannelOrg"), myTargetModel.getModelPathMap().get("PolicyEntity.PartyList[]"), "Policy.SalesChannelOrg", "PolicyEntity.PartyList[]", 0, true, p_3,
    (String[] m_4) ->
    {
    {
    String fieldValue = null;
    fieldValue = "2";
    myTargetModel.getFieldPathMap().get(m_4[1] + ".ContactType").setValue(fieldValue); 
    
    }
    {
    String fieldValue = null;
    fieldValue = mySourceModel.getFieldPathMap().get(m_4[0] + ".PartyName").getValue();
    myTargetModel.getFieldPathMap().get(m_4[1] + ".PartyName").setValue(fieldValue); 
    
    }
    {
    java.util.function.Predicate<String> p_4 = null;
    p_4 = (String f_2) -> (mySourceModel.getFieldPathMap().get(f_2 + ".IsPrimaryContact").getValue().equals("Y"));
    com.github.jmodel.mapper.api.ModelHelper.arrayMapping(mySourceModel, myTargetModel, mySourceModel.getModelPathMap().get("Policy.SalesChannelOrg.PartyContactList[]"), myTargetModel.getModelPathMap().get("PolicyEntity.PartyList[]"), "Policy.SalesChannelOrg.PartyContactList[]", "PolicyEntity.PartyList[]", 0, false, p_4,
    (String[] m_5) ->
    {
    {
    String fieldValue = null;
    fieldValue = mySourceModel.getFieldPathMap().get(m_5[0] + ".Name").getValue();
    myTargetModel.getFieldPathMap().get(m_4[1] + ".ContactName").setValue(fieldValue); 
    
    }
    });
    }
    {
    java.util.function.Predicate<String> p_5 = null;
    p_5 = (String f_3) -> (mySourceModel.getFieldPathMap().get(f_3 + ".IsPrimaryAddress").getValue().equals("Y"));
    com.github.jmodel.mapper.api.ModelHelper.arrayMapping(mySourceModel, myTargetModel, mySourceModel.getModelPathMap().get("Policy.SalesChannelOrg.PartyAddressList[]"), myTargetModel.getModelPathMap().get("PolicyEntity.PartyList[]"), "Policy.SalesChannelOrg.PartyAddressList[]", "PolicyEntity.PartyList[]", 0, false, p_5,
    (String[] m_6) ->
    {
    {
    String fieldValue = null;
    fieldValue = ((((((((mySourceModel.getFieldPathMap().get(m_6[0] + ".Country").getValue()+";")+mySourceModel.getFieldPathMap().get(m_6[0] + ".Province").getValue())+";")+mySourceModel.getFieldPathMap().get(m_6[0] + ".City").getValue())+";")+mySourceModel.getFieldPathMap().get(m_6[0] + ".AddressLine1").getValue())+";")+mySourceModel.getFieldPathMap().get(m_6[0] + ".AddressLine1").getValue());
    myTargetModel.getFieldPathMap().get(m_4[1] + ".ContactAddress").setValue(fieldValue); 
    
    }
    });
    }
    });
    }
    }
    {
    java.util.function.Predicate<String> p_6 = null;
    com.github.jmodel.mapper.api.ModelHelper.arrayMapping(mySourceModel, myTargetModel, mySourceModel.getModelPathMap().get("Policy.PolicyLobList[]"), myTargetModel.getModelPathMap().get("PolicyEntity"), "Policy.PolicyLobList[]", "PolicyEntity", 0, false, p_6,
    (String[] m_7) ->
    {
    {
    String fieldValue = null;
    fieldValue = mySourceModel.getFieldPathMap().get(m_7[0] + ".ProductId").getValue();
    myTargetModel.getFieldPathMap().get(m_7[1] + ".ProductCode").setValue(fieldValue); 
    
    }
    {
    java.util.function.Predicate<String> p_7 = null;
    com.github.jmodel.mapper.api.ModelHelper.arrayMapping(mySourceModel, myTargetModel, mySourceModel.getModelPathMap().get(m_7[0] + ".PolicyRiskList[]"), myTargetModel.getModelPathMap().get(m_7[1] + ".InsuredList[]"), m_7[0] + ".PolicyRiskList[]", m_7[1] + ".InsuredList[]", Integer.valueOf(m_7[2]), false, p_7,
    (String[] m_8) ->
    {
    {
    String fieldValue = null;
    fieldValue = "RiskType";
    myTargetModel.getFieldPathMap().get(m_8[1] + ".Type").setValue(fieldValue); 
    
    }
    {
    String fieldValue = null;
    fieldValue = mySourceModel.getFieldPathMap().get(m_8[0] + ".Country").getValue();
    myTargetModel.getFieldPathMap().get(m_8[1] + ".CountryCode").setValue(fieldValue); 
    
    }
    {
    String fieldValue = null;
    fieldValue = "3";
    myTargetModel.getFieldPathMap().get(m_8[1] + ".InsuredCategory").setValue(fieldValue); 
    
    }
    {
    String fieldValue = null;
    fieldValue = ((((((((mySourceModel.getFieldPathMap().get(m_8[0] + ".Country").getValue()+";")+mySourceModel.getFieldPathMap().get(m_8[0] + ".Province").getValue())+";")+mySourceModel.getFieldPathMap().get(m_8[0] + ".City").getValue())+";")+mySourceModel.getFieldPathMap().get(m_8[0] + ".District").getValue())+";")+mySourceModel.getFieldPathMap().get(m_8[0] + ".Address").getValue());
    myTargetModel.getFieldPathMap().get(m_8[1] + ".InsuredName").setValue(fieldValue); 
    
    }
    {
    java.util.function.Predicate<String> p_8 = null;
    p_8 = (String f_4) -> (mySourceModel.getFieldPathMap().get(f_4 + ".SumInsured").getValue()!=null);
    com.github.jmodel.mapper.api.ModelHelper.arrayMapping(mySourceModel, myTargetModel, mySourceModel.getModelPathMap().get(m_8[0]), myTargetModel.getModelPathMap().get(m_8[1] + ".PolicyLimit"), m_8[0], m_8[1] + ".PolicyLimit", Integer.valueOf(m_8[2]), false, p_8,
    (String[] m_9) ->
    {
    {
    String fieldValue = null;
    fieldValue = "PolicyLimitType";
    myTargetModel.getFieldPathMap().get(m_9[1] + ".Type").setValue(fieldValue); 
    
    }
    {
    java.util.function.Predicate<String> p_9 = null;
    com.github.jmodel.mapper.api.ModelHelper.arrayMapping(mySourceModel, myTargetModel, mySourceModel.getModelPathMap().get(m_9[0]), myTargetModel.getModelPathMap().get(m_9[1] + ".PolicyLimitValue[]"), m_9[0], m_9[1] + ".PolicyLimitValue[]", Integer.valueOf(m_9[2]), false, p_9,
    (String[] m_10) ->
    {
    {
    String fieldValue = null;
    fieldValue = "PolicyLimitValueType";
    myTargetModel.getFieldPathMap().get(m_10[1] + ".Type").setValue(fieldValue); 
    
    }
    {
    String fieldValue = null;
    fieldValue = "0";
    myTargetModel.getFieldPathMap().get(m_10[1] + ".IsAllowance").setValue(fieldValue); 
    
    }
    {
    String fieldValue = null;
    fieldValue = mySourceModel.getFieldPathMap().get(m_10[0] + ".SumInsured").getValue();
    myTargetModel.getFieldPathMap().get(m_10[1] + ".LimitValue").setValue(fieldValue); 
    
    }
    {
    String fieldValue = null;
    fieldValue = "AOA";
    myTargetModel.getFieldPathMap().get(m_10[1] + ".ModeType").setValue(fieldValue); 
    
    }
    {
    String fieldValue = null;
    fieldValue = "AMOUNT";
    myTargetModel.getFieldPathMap().get(m_10[1] + ".ValueType").setValue(fieldValue); 
    
    }
    });
    }
    });
    }
    {
    java.util.function.Predicate<String> p_10 = null;
    com.github.jmodel.mapper.api.ModelHelper.arrayMapping(mySourceModel, myTargetModel, mySourceModel.getModelPathMap().get(m_8[0] + ".PolicyCoverageList[]"), myTargetModel.getModelPathMap().get(m_8[1] + ".CoverageList[]"), m_8[0] + ".PolicyCoverageList[]", m_8[1] + ".CoverageList[]", Integer.valueOf(m_8[2]), false, p_10,
    (String[] m_11) ->
    {
    {
    String fieldValue = null;
    fieldValue = "PolicyCoverageType";
    myTargetModel.getFieldPathMap().get(m_11[1] + ".Type").setValue(fieldValue); 
    
    }
    {
    String fieldValue = null;
    fieldValue = (mySourceModel.getFieldPathMap().get(m_8[0] + ".InsuredId").getValue()+"-3");
    myTargetModel.getFieldPathMap().get(m_11[1] + ".PolicyRiskNo").setValue(fieldValue); 
    
    }
    {
    String fieldValue = null;
    fieldValue = mySourceModel.getFieldPathMap().get(m_7[0] + ".ProductId").getValue();
    myTargetModel.getFieldPathMap().get(m_11[1] + ".ProductCode").setValue(fieldValue); 
    
    }
    {
    java.util.function.Predicate<String> p_11 = null;
    com.github.jmodel.mapper.api.ModelHelper.arrayMapping(mySourceModel, myTargetModel, mySourceModel.getModelPathMap().get(m_7[0] + ".PolicyCoverageList[]"), myTargetModel.getModelPathMap().get(m_8[1] + ".CoverageList[]"), m_7[0] + ".PolicyCoverageList[]", m_8[1] + ".CoverageList[]", Integer.valueOf(m_8[2]), true, p_11,
    (String[] m_12) ->
    {
    {
    String fieldValue = null;
    fieldValue = "PolicyCoverageType";
    myTargetModel.getFieldPathMap().get(m_12[1] + ".Type").setValue(fieldValue); 
    
    }
    {
    String fieldValue = null;
    fieldValue = mySourceModel.getFieldPathMap().get(m_7[0] + ".ProductId").getValue();
    myTargetModel.getFieldPathMap().get(m_12[1] + ".ProductCode").setValue(fieldValue); 
    
    }
    });
    }
    });
    }
    });
    }
    });
    }
  }
}
