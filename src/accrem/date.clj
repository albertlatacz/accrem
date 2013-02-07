;;; date.clj -- date functions for Clojure

;; Islon Scherer, islonscherer@gmail.com
;; May 21, 2010

(ns
  #^{:author "Islon Scherer",
     :doc "This file defines utility functions for date
           manipulation, generation and formatting."}
  accrem.date
  (:import (java.util Date Calendar Locale)
	   (java.text SimpleDateFormat)))

(defn #^Date date
  "Creates a new java.util.Date for the specified stamp."
  [stamp]
     (Date. stamp))

(defn #^Date now
  "The current date."
  [] (Date.))

(defn #^String format-date
  "Uses SimpleDateFormat to format the date d (default: now) using the format fm.
   See java.text.SimpleDateFormat for pattern information.
   e.g.: (format-date (date 400734000000) \"yyyy-MM-dd HH:mm:ss\")
   returns \"1982-09-13 00:00:00\"."
  ([fm] (format-date (now) fm))
  ([d fm]
     (.format (SimpleDateFormat. fm) d)))

(defn #^Date parse-date
  "Parse a string into a date using the supplied format.
   e.g.: (parse-date \"1999-12-31 23:59\" \"yyyy-MM-dd HH:mm\")."
  [s fm]
  (.parse (SimpleDateFormat. fm) s))

(defn #^Date +date
  "Adds or subtracts time from the date d according to
   a map of parameters.
   Possible parameters (map keys) are:
   :milliseconds, :seconds, :minutes, :hours, :days,
   :months and :years.
   e.g: three years ago: (+date (now) {:years -3})
        five days and two months into the future:
        (+date (now) {:months 2, :days 5})"
  [d m]
  (let [cal (Calendar/getInstance)
	f (fn [[k v]]
	    (cond (= k :milliseconds)
		  (.add cal Calendar/MILLISECOND v)
		  (= k :seconds)
		  (.add cal Calendar/SECOND v)
		  (= k :minutes)
		  (.add cal Calendar/MINUTE v)
		  (= k :hours)
		  (.add cal Calendar/HOUR v)
		  (= k :days)
		  (.add cal Calendar/DAY_OF_MONTH v)
		  (= k :months)
		  (.add cal Calendar/MONTH v)
		  (= k :years)
		  (.add cal Calendar/YEAR v)))]
    (.setTime cal d)
    (doall (map f m))
    (.getTime cal)))

(defn locale
  "Creates a locale for the specified language or language and country."
  ([l] (Locale. l))
  ([l c] (Locale. l c)))
