(ns accrem.views.sections
  (:use accrem.views.client
        accrem.views.common
        accrem.models.client
        noir.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers))


(defn account-details-section [accountType accountDetails]
  (page-section "Account details"
    [:div {}
     (field-hidden :_id (:_id accountDetails))
     (field-hidden :accountType accountType)
     (field-text :accountId "Id" (:accountId accountDetails))
     (field-text :accountName "Account name" (:accountName accountDetails))
     (field-text-area :accountNotes "Notes" (:accountNotes accountDetails))
     ]))


(defn account-header-section [accountType accountDetails]
  [:div {:class "well clearfix form-vertical"}
   (field-hidden :_id (:_id accountDetails))
   (field-hidden :accountType accountType)
   (field-text :accountId "Id" (:accountId accountDetails))
   (field-text :accountName "Account name" (:accountName accountDetails))
   (field-text-area :accountNotes "Notes" (:accountNotes accountDetails))
   ])


(defn client-contact-section [contact]
  (page-section "Contact"
    [:div {}
     (field-text :contactName "Name" (:contactName contact))
     (field-text-area :contactAddress "Address" (:contactAddress contact))
     (field-text :contactPostcode "Postcode" (:contactPostcode contact))
     (field-text :contactTelephone "Telephone" (:contactTelephone contact))
     (field-text :contactFax "Fax" (:contactFax contact))
     (field-text :contactMobile "Mobile" (:contactMobile contact))
     (field-email :contactEmail "Email" (:contactEmail contact))
     (field-text :contactWeb "Web" (:contactWeb contact))
     ]))


(defn service-plan-section [plan]
  (page-section "Service plan"
    [:div {}
     (field-text :servicePlanName "Service plan name" (:servicePlanName plan))
     (field-text :servicePlanCharge "Charge" (:servicePlanCharge plan))
     (field-dropdown :servicePlanChargeFreq "" '("per month" "per quarter" "per annum") (:servicePlanChargeFreq plan))
     (field-date :servicePlanStartedDate "Contract start date" (:servicePlanStartedDate plan))
     (field-date :servicePlanReviewDate "Contract review date" (:servicePlanReviewDate plan))
     ]))


(defn bank-account-section [bankAccount]
  (page-section "Bank account"
    [:div {}
     (field-text :bankAccountBankName "Bank name" (:bankAccountBankName bankAccount))
     (field-text :bankAccountBankAddress "Bank address" (:bankAccountBankAddress bankAccount))
     (field-text :bankAccountSortCode "Sort code" (:bankAccountSortCode bankAccount))
     (field-text :bankAccountNumber "Number" (:bankAccountNumber bankAccount))
     (field-text :bankAccountName "Account name" (:bankAccountName bankAccount))
     ]))


(defn personal-details-section [personalDetails]
  (page-section "Personal details"
    [:div {}
     (field-text :personalDetailsNI "NI number" (:personalDetailsNI personalDetails))
     (field-text :personalDetailsUTR "UTR number" (:personalDetailsUTR personalDetails))
     (field-date :personalDetailsDOB "Date of birth" (:personalDetailsDOB personalDetails))
     ]))


(defn self-employment-section [selfEmployment]
  (page-section "Self Employment"
    [:div {}
     (field-date :selfEmploymentStartedDate "Started date" (:selfEmploymentStartedDate selfEmployment))
     (field-date :selfEmploymentFinishedDate "Finished date" (:selfEmploymentFinishedDate selfEmployment))
     ]))



(defn self-assessment-section [selfAssessment]
  (page-section "Self Assessment"
    [:div {}
     (field-dropdown :selfAssessment "Self Assessment" '("Yes" "No") (:selfAssessment selfAssessment))
     (field-text :selfAssessmentLastReturn "Last return" (:selfAssessmentLastReturn selfAssessment))
     (field-date :selfAssessmentFilingDate "Filing date" (:selfAssessmentFilingDate selfAssessment))
     (field-text :selfAssessmentTaxDue "Tax due" (:selfAssessmentTaxDue selfAssessment))
     (field-text-area :selfAssessmentNotes "Notes" (:selfAssessmentNotes selfAssessment))
     ]))


(defn company-details-section [companyDetails]
  (page-section "Company details"
    [:div {}

     (field-text :companyDetailsRegName "Company name" (:companyDetailsRegName companyDetails))
     (field-text :companyDetailsRegNumber "Registration number" (:companyDetailsRegNumber companyDetails))
     (field-text-area :companyDetailsRegAddress "Registered address" (:companyDetailsRegAddress companyDetails))
     (field-text :companyDetailsTradingName "Trading name" (:companyDetailsTradingName companyDetails))
     (field-text :companyDetailsTaxReference "Tax reference" (:companyDetailsTaxReference companyDetails))
     (field-date :companyDetailsIncorporationDate "Incorporation date" (:companyDetailsIncorporationDate companyDetails))
     (field-text :companyDetailsSharesIssued "Shares issued" (:companyDetailsSharesIssued companyDetails))
     (field-date :companyDetailsFinancialYearEndDate "Financial year end" (:companyDetailsFinancialYearEndDate companyDetails))
     ]))

(defn annual-accounts-section [annualAccounts]
  (page-section "Annual accounts"
    [:div {}
     (field-date :companiesHouseAnnualReturnDate "CH AR01 annual return due" (:companiesHouseAnnualReturnDate annualAccounts))
     (field-date :companiesHouseAnnualReturnLastFilingDate "CH AR01 last filing date" (:companiesHouseAnnualReturnLastFilingDate annualAccounts))
     (field-date :companiesHouseAnnualAccountsDate "CH AA01 annual accounts due" (:companiesHouseAnnualAccountsDate annualAccounts))
     (field-date :companiesHouseAnnualAccountsLastFiledDate "CH AA01 annual accounts last filed" (:companiesHouseAnnualAccountsLastFiledDate annualAccounts))
     (field-date :companiesHouseAnnualAccountsLastFilingDate "CH AA01 annual accounts last filing date" (:companiesHouseAnnualAccountsLastFilingDate annualAccounts))
     [:br ]
     [:hr ]
     (field-date :hmrcCorporationTaxDate "Corporation tax due" (:hmrcCorporationTaxDate annualAccounts))
     (field-date :hmrcCorporationTaxLastFilingDate "Corporation tax last filing date" (:hmrcCorporationTaxLastFilingDate annualAccounts))
     (field-date :hmrcAnnualAccountsDate "CT600 annual accounts due" (:hmrcAnnualAccountsDate annualAccounts))
     (field-date :hmrcAnnualAccountsLastFiledDate "CT600 annual accounts last filed" (:hmrcAnnualAccountsLastFiledDate annualAccounts))
     (field-date :hmrcAnnualAccountsLastFilingDate "CT600 annual accounts last filing date" (:hmrcAnnualAccountsLastFilingDate annualAccounts))
     ]))


(defn payroll-section [payroll]
  (page-section "Payroll"
    [:div {}
     (field-text :payrollAccountsOfficeRef "Accounts Office ref" (:payrollAccountsOfficeRef payroll))
     (field-text :payrollEmployerPayeRef "Employer PAYE ref" (:payrollEmployerPayeRef payroll))
     (field-dropdown :payrollCISRegistered "CIS registered" '("Yes" "No") (:payrollCISRegistered payroll))
     ]))


(defn vat-details-section [vatDetails]
  (page-section "VAT Details"
    [:div {}
     (field-dropdown :vatDetailsRegistered "VAT registered" '("Yes" "No") (:vatDetailsRegistered vatDetails))
     (field-text :vatDetailsNumber "VAT Number" (:vatDetailsNumber vatDetails))
     (field-date :vatDetailsRegistrationDate "Registration date" (:vatDetailsRegistrationDate vatDetails ""))
     (field-dropdown :vatDetailsReturnsSchedule "Returns schedule" '("January, April, July, October" "February, May, August, November" "March, June, September, December") (:vatDetailsReturnsSchedule vatDetails))
     (field-dropdown :vatDetailsLastFiledOnline "Last filed online" '("Yes" "No") (:vatDetailsLastFiledOnline vatDetails))
     (field-date :vatDetailsLastReturnDate "Last return date" (:vatDetailsLastReturnDate vatDetails))
     ]))



(defn associated-individuals-section [associatedIndividual]
  (page-section "Associated individuals"
    [:div {}
     [:table#associatedIndividuals {:class "table table-striped table-bordered"}
      (mapcat #(vector (render-associated-individual (client-by-id (first %)) (second %)))
        (map vector (:associatedIndividualsIds associatedIndividual)
          (:associatedIndividualsTypes associatedIndividual)))
      ]

     (render-select-individual-to-associate (clients-with-type :individual ))
     ]))

