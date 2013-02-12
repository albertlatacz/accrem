(ns accrem.views.bindings
  (:use accrem.views.common
        accrem.views.admin
        accrem.views.dashboard
        accrem.views.user
        accrem.views.company
        accrem.views.soletrader
        accrem.views.individual
        accrem.views.tasks
        noir.core))

; companies
(defpage [:get (url-list-companies :pageId )] {:keys [pageId]}
  (render-list-companies pageId))

(defpage [:get (url-add-company)] {:as client}
  (render-add-company client))

(defpage [:post (url-add-company)] {:as client}
  (render-validate-and-add-company client))

(defpage [:get (url-edit-company :id )] {:keys [id]}
  (render-edit-company-by-id id))

(defpage [:post (url-edit-company :id )] {:as client}
  (render-validate-and-edit-company client))

(defpage [:post (url-delete-company :id )] {:keys [id]}
  (render-delete-company id))


; individuals
(defpage [:get (url-list-individuals :pageId )] {:keys [pageId]}
  (render-list-individuals pageId))

(defpage [:get (url-add-individual)] {:as client}
  (render-add-individual client))

(defpage [:post (url-add-individual)] {:as client}
  (render-validate-and-add-individual client))

(defpage [:get (url-edit-individual :id )] {:keys [id]}
  (render-edit-individual-by-id id))

(defpage [:post (url-edit-individual :id )] {:as client}
  (render-validate-and-edit-individual client))

(defpage [:post (url-delete-individual :id )] {:keys [id]}
  (render-delete-individual id))


; sole traders
(defpage [:get (url-list-sole-traders :pageId )] {:keys [pageId]}
  (render-list-sole-traders pageId))

(defpage [:get (url-add-sole-trader)] {:as client}
  (render-add-sole-trader client))

(defpage [:post (url-add-sole-trader)] {:as client}
  (render-validate-and-add-sole-trader client))

(defpage [:get (url-edit-sole-trader :id )] {:keys [id]}
  (render-edit-sole-trader-by-id id))

(defpage [:post (url-edit-sole-trader :id )] {:as client}
  (render-validate-and-edit-sole-trader client))

(defpage [:post (url-delete-sole-trader :id )] {:keys [id]}
  (render-delete-sole-trader id))


; tasks
(defpage [:get (url-add-client-task)] {:keys [clientId]}
  (render-add-client-task clientId))

(defpage [:post (url-add-client-task)] {:as task}
  (render-validate-and-add-client-task task))

(defpage [:get (url-edit-client-task)] {:keys [taskId]}
  (render-edit-client-task taskId))

(defpage [:post (url-edit-client-task)] {:as task}
  (render-validate-and-edit-client-task task))

(defpage [:get (url-list-client-tasks)] {:keys [clientId pageId]}
  (render-list-client-tasks clientId pageId))

(defpage [:post (url-delete-client-task)] {:keys [taskId]}
  (render-delete-task taskId))



; login
(defpage [:get (url-login)] {:as user}
  (render-login))

(defpage "/logout" {}
  (render-logout))

(defpage [:post (url-login)] {:as user}
  (render-validate-and-login user))

; dashboard
(defpage "/" []
  (render-redirect-to-dashboard))

(defpage [:get (url-dashboard)] {:as request}
  (render-dashboard request))

;setup
(defpage [:get (url-setup)] {:as request}
  (render-setup request))

(defpage [:post (url-setup)] {:as request}
  (render-validate-and-save-setup request))


; redirects
(pre-route "/login" {}
  (redirect->setup))

(pre-route "/app/*" {}
  (redirect->authentication))



