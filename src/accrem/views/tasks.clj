(ns accrem.views.tasks
  (:require [noir.validation :as vali])

  (:use accrem.config
        accrem.views.common
        accrem.views.client
        accrem.models.tasks
        accrem.models.client
        noir.core
        hiccup.core
        hiccup.form-helpers
        hiccup.page-helpers))


(defn valid-task? [task]
  (vali/rule (vali/has-value? (:dueDate task))
    [:dueDate "Required field"])

  (vali/rule (vali/has-value? (:title task))
    [:title "Required field"])

  (not (vali/errors? :dueDate :title )))


(defn render-task-links [task]
  [:div {}
   [:div {:align "center"}
    [:a {:class "btn" :href (url-edit-client-task (:_id task))} [:i {:class "icon-edit"}] " Edit"]
    "&nbsp;&nbsp;"
    [:a {:class "btn btn-danger" :data-toggle "modal" :href (str "#confirmDelete" (:_id task))} [:i {:class "icon-trash icon-white"}] " Delete"]
    ]

   [:div {:id (str "confirmDelete" (:_id task)) :class "modal hide fade"}
    [:div {:class "modal-header"}
     [:button {:type "button" :class "close" :data-dismiss "modal"} "×"]
     [:h3 "Delete task"]
     ]

    [:div {:class "modal-body"}
     "Are you sure you would like to delete this task?"
     [:br ]
     "Due date: " (:dueDate task) [:br ]
     "Title: " (:title task) [:br ]
     ]

    [:div {:class "modal-footer"}
     [:a {:href "#" :class "btn" :data-dismiss "modal"} "Cancel"]
     [:a {:href (url-delete-client-task (:_id task)) :class "btn btn-danger"} [:i {:class "icon-trash icon-white"}] " Delete"]]

    ]]
  )


(defn render-task-row-with-actions [task]
  [:tr {}
   [:td (:dueDate task)]
   [:td (:title task)]
   [:td (:status task)]
   [:td {:width "150"} (render-task-links task)]]
  )


(defn render-task-row [task]
  [:tr {}
   [:td (:dueDate task)]
   [:td (:title task)]
   [:td (:status task)]]
  )


(defn render-task-list [clientId tasks page]
  (render-paged-list tasks page
    #(vector "Expiration Date" "Title" "Status" "Action")
    #(render-task-row-with-actions %)
    #(url-list-client-tasks clientId %)))

(defn task-details-section [detailsType task]
  (page-section "Task details"
    [:div {}
     (field-hidden :_id (:_id task))
     (field-hidden :clientId (:clientId task))
     (field-hidden :type detailsType)
     (field-date :dueDate "Due date" (:dueDate task))
     (field-text :title "Title" (:title task))
     (field-dropdown :status "Status" '("Created" "In Progress" "Completed") (:status task))
     (field-text-area :notes "Notes" (:notes task))
     ]))



(defn task-fields [task]
  [:div {:class "row"}
   [:div {:class "span6"}
    (task-details-section :reminder task)
    ]

   [:div {:class "span6"}
    ]]
  )

(defn task-details-page [title formSubmitButton formLink task]
  (page-layout title :tasks {}
    (form-to {:class "form-horizontal"} [:post formLink]
      (task-fields task)

      [:div {:class "well"}
       [:div {:align "right"}
        [:a {:href (url-list-client-tasks (:clientId task)) :class "btn"} "Cancel"]
        [:span "&nbsp;&nbsp;"]
        (submit-button {:class "btn btn-primary"} formSubmitButton)
        ]])))


(defn task-action-completed-page [task action]
  (let [actionText (str "Task " (name action))]
    (page-layout actionText :tasks task
      [:div {:class "well clearfix span6 offset3 row"}
       [:div [:h3 actionText]
        [:hr ]]

       [:div [:p "The following task has been " action]
        [:div {:class "well"}
         [:br ]
         [:p (str "Title: " (:title task))]
         [:p (str "Due date: " (:dueDate task))]
         [:p (str "Status: " (:status task))]
         [:p (str "Notes: " (:notes task))]
         ]
        [:hr ]
        ]

       [:div {:class "right"}
        [:a {:href (url-list-client-tasks (:clientId task)) :class "btn btn-primary"} "Finish"]
        ]
       ])))


(defpage [:get (url-list-client-tasks)] {:keys [clientId pageId]}
  (let [client (client-by-id clientId)
        page (if (empty? pageId) 1 (. Integer valueOf pageId))]
    (page-layout "Tasks" (:clientType client) {}
      [:div {}
       [:h3 {:class "left"} (render-client-link client) " tasks"]
       [:a {:href (url-add-client-task clientId) :class "btn btn-primary right"} "Add"]
       [:br ]
       [:br ]
       (render-task-list clientId (tasks-for-client clientId) page)]
      )))



(defpage [:get (url-add-client-task)] {:keys [clientId]}
  (task-details-page "Add" "Add task" (url-add-client-task clientId) {:clientId clientId}))


(defpage [:post (url-add-client-task)] {:as task}
  (if (valid-task? task)
    (do
      (insert-task! task)
      (task-action-completed-page task :added ))
    (task-details-page "Add" "Add task" (url-add-client-task (:clientId task)) task)))



(defpage [:get (url-edit-client-task)] {:keys [taskId]}
  (task-details-page "Edit" "Save" (url-edit-client-task taskId) (task-by-id taskId)))

(defpage [:post (url-edit-client-task)] {:as task}
  (if (valid-task? task)
    (do
      (update-task! task)
      (task-action-completed-page task :saved ))
    (task-details-page "Edit" "Save" (url-edit-client-task (:clientId task)) task)))


;TODO change to post
(defpage [:get (url-delete-client-task)] {:keys [taskId]}
  (do
    (let [taskToDelete (task-by-id taskId)]
      (delete-task! taskToDelete)
      (task-action-completed-page taskToDelete :deleted ))))
