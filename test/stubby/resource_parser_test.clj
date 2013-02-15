(ns stubby.resource-parser-test
  (:use midje.sweet stubby.resource-parser))

(fact "body of the response is the content of the file if there is no header"
  (:body (first (parse-responses "content"))) => "content")

(fact "should get single response from a file with both its header and body"
  (let [responses (parse-responses "---\n{\n\"status\":\"201\"\n}\n---\ncontent")]
    (:status (:headers (first responses))) => "201"
    (:body (first responses)) => "\ncontent"))

(fact "should get mutilple responses from a file with both their headers and bodies"
  (let [responses (parse-responses "---\n{\n\"status\":\"201\"\n}\n---\ncontent1\n---\n{\n\"status\":\"404\"\n}\n---\ncontent2\n")]
    (:status (:headers (first responses))) => "201"
    (:body (first responses)) => "\ncontent1\n"
    (:status (:headers (second responses))) => "404"
    (:body (second responses)) => "\ncontent2\n"))

(fact "should get mutilple responses from a file with both their headers and bodies even if a trailing body is missing"
  (let [responses (parse-responses "---\n{\n\"status\":\"201\"\n}\n---\ncontent1\n---\n{\n\"status\":\"404\"\n}\n---")]
    (:status (:headers (first responses))) => "201"
    (:status (:headers (second responses))) => "404"
    (:body (first responses)) => "\ncontent1\n"
    (:body (second responses)) => nil))

(fact "should get first response without a query string from file if none is specified from a file of many responses"
  (let [response (find-response nil "---\n{\n\"status\":\"201\",\n\"querystring\":\"a=b\"\n}\n---\ncontent1\n---\n{\n\"status\":\"404\"\n}\n---\ncontent2\n")]
    (:status (:headers response)) => "404"
    (:body response) => "\ncontent2\n"))

(fact "should get single response from file with a matching query string"
  (let [response (find-response "a=b" "---\n{\n\"status\":\"201\",\n\"querystring\":\"a=b\"\n}\n---\ncontent1\n---\n{\n\"status\":\"404\"\n}\n---\ncontent2\n")]
    (:status (:headers response)) => "201"
    (:body response) => "\ncontent1\n"))
