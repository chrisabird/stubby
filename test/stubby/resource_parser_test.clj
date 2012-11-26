(ns stubby.resource-parser-test
  (:use midje.sweet stubby.resource-parser))

(fact "body of the resource is the content of the file if there is no header"
  (:body (parse-resource "content")) => "content")

(fact "should include front matter json as well as the following body"
  (let [resource (parse-resource "---\n{\n\"status\":\"201\"\n}\n---\ncontent")]
    (:status resource) => "201"))
