package com.sangupta.logparser.charles;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sangupta.jerry.encoder.Base64Encoder;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.StringUtils;

public class CharlesSessionXmlLogParser {
	
	public static CharlesSession parse(String logData) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		
		dbFactory.setFeature("http://xml.org/sax/features/namespaces", false);
		dbFactory.setFeature("http://xml.org/sax/features/validation", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(logData)));
		
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();
		
		NodeList root = doc.getElementsByTagName("charles-session");
		if(root == null || root.getLength() == 0) {
			return null;
		}
		
		return parseSession(root.item(0));
	}

	private static CharlesSession parseSession(Node root) {
		if(root.getNodeType() != Node.ELEMENT_NODE) {
			return null;
		}
		
		Element rootElement = (Element) root;
		
		CharlesSession session = new CharlesSession();
		
		NodeList transactions = rootElement.getElementsByTagName("transaction");
		if(transactions == null || transactions.getLength() == 0) {
			return session;
		}
		
		for(int index = 0; index < transactions.getLength(); index++) {
			CharlesTransaction transaction = parseTransaction(transactions.item(index));
			if(transaction != null) {
				session.transactions.add(transaction);
			}
		}
		
		return session;
	}

	private static CharlesTransaction parseTransaction(Node item) {
		if(item.getNodeType() != Node.ELEMENT_NODE) {
			return null;
		}
		
		Element transactionElement = (Element) item;
		
		CharlesTransaction transaction = new CharlesTransaction();
		
		transaction.status = transactionElement.getAttribute("status");
		transaction.method = transactionElement.getAttribute("method");
		transaction.protocolVersion = transactionElement.getAttribute("protocolVersion");
		transaction.protocol = transactionElement.getAttribute("protocol");
		transaction.host = transactionElement.getAttribute("host");
		transaction.path = transactionElement.getAttribute("path");
		transaction.actualPort = StringUtils.getIntValue(transactionElement.getAttribute("actualPort"), -1);
		transaction.remoteAddress = transactionElement.getAttribute("remoteAddress");
		transaction.clientAddress = transactionElement.getAttribute("clientAddress");
		
		transaction.startTime = transactionElement.getAttribute("startTime");
		transaction.startTimeMillis = StringUtils.getLongValue(transactionElement.getAttribute("startTimeMillis"), -1);
		
		transaction.endTime = transactionElement.getAttribute("endTime");
		transaction.endTimeMillis = StringUtils.getLongValue(transactionElement.getAttribute("endTimeMillis"), -1);
		
		transaction.requestBeginTime = transactionElement.getAttribute("requestBeginTime");
		transaction.requestBeginTimeMillis = StringUtils.getLongValue(transactionElement.getAttribute("requestBeginTimeMillis"), -1);
		
		transaction.requestTime = transactionElement.getAttribute("requestTime");
		transaction.requestTimeMillis = StringUtils.getLongValue(transactionElement.getAttribute("requestTimeMillis"), -1);
		
		transaction.responseTime = transactionElement.getAttribute("responseTime");
		transaction.responseTimeMillis = StringUtils.getLongValue(transactionElement.getAttribute("responseTimeMillis"), -1);
		
		transaction.latency = StringUtils.getIntValue(transactionElement.getAttribute("latency"), -1);
		transaction.duration = StringUtils.getIntValue(transactionElement.getAttribute("duration"), -1);
		transaction.requestDuration = StringUtils.getIntValue(transactionElement.getAttribute("requestDuration"), -1);
		transaction.responseDuration = StringUtils.getIntValue(transactionElement.getAttribute("responseDuration"), -1);
		transaction.dnsDuration = StringUtils.getIntValue(transactionElement.getAttribute("dnsDuration"), -1);
		transaction.connectDuration = StringUtils.getIntValue(transactionElement.getAttribute("connectDuration"), -1);
		
		transaction.overallSpeed = StringUtils.getIntValue(transactionElement.getAttribute("overallSpeed"), -1);
		transaction.responseSpeed = StringUtils.getIntValue(transactionElement.getAttribute("responseSpeed"), -1);
		
		transaction.totalSize = StringUtils.getLongValue(transactionElement.getAttribute("totalSize"), -1);
		
		NodeList request = transactionElement.getElementsByTagName("request");
		if(request != null && request.getLength() > 0) {
			transaction.request = parseRequest(request.item(0));
		}
		
		NodeList response = transactionElement.getElementsByTagName("response");
		if(response != null && response.getLength() > 0) {
			transaction.response = parseResponse(response.item(0));
		}
		
		return transaction;
	}

	private static CharlesResponse parseResponse(Node item) {
		if(item.getNodeType() != Node.ELEMENT_NODE) {
			return null;
		}
		
		Element element = (Element) item;
		
		CharlesResponse response = new CharlesResponse();
		
		response.status = StringUtils.getIntValue(element.getAttribute("status"), -1);
		response.headersLength = StringUtils.getIntValue(element.getAttribute("headers"), -1);
		response.bodyLength = StringUtils.getIntValue(element.getAttribute("body"), -1);
		
		response.headers = parseHeaders(element);
		response.body = parseBody(element);
		
		return response;
	}

	private static CharlesRequest parseRequest(Node item) {
		if(item.getNodeType() != Node.ELEMENT_NODE) {
			return null;
		}
		
		Element element = (Element) item;
		
		CharlesRequest request = new CharlesRequest();
		
		request.headersLength = StringUtils.getIntValue(element.getAttribute("headers"), -1);
		request.bodyLength = StringUtils.getIntValue(element.getAttribute("body"), -1);
		
		request.headers = parseHeaders(element);
		request.body = parseBody(element);
		
		return request;
	}

	private static CharlesBody parseBody(Element root) {
		Element element = getChildElement(root, "body");
		if(element == null) {
			return null;
		}
		
		CharlesBody body = new CharlesBody();
		
		body.encoding = element.getAttribute("encoding");
		String data = element.getTextContent();
		
		if(AssertUtils.isEmpty(data)) {
			return body;
		}
		
		if("base64".equalsIgnoreCase(body.encoding)) {
			body.data = Base64Encoder.decode(data);
		} else {
			body.data = data.getBytes();
		}
		
		return body;
	}

	private static CharlesHeaders parseHeaders(Element root) {
		Element element = getChildElement(root, "headers");
		if(element == null) {
			return null;
		}
		
		CharlesHeaders headers = new CharlesHeaders();
		
		headers.firstLine = getChildNodeValue(element, "first-line");
		
		// all headers
		NodeList list = element.getElementsByTagName("header");
		if(list != null && list.getLength() > 0) {
			for(int index = 0; index < list.getLength(); index++) {
				headers.list.add(parseHeader(list.item(index)));
			}
		}
		
		return headers;
	}

	private static CharlesHeader parseHeader(Node node) {
		if(node.getNodeType() != Node.ELEMENT_NODE) {
			return null;
		}
		
		Element root = (Element) node;
		
		CharlesHeader header = new CharlesHeader();
		
		header.name = getChildNodeValue(root, "name");
		header.value = getChildNodeValue(root, "value");
		
		return header;
	}

	private static String getChildNodeValue(Element root, String tagName) {
		Element element = getChildElement(root, tagName);
		if(element == null) {
			return null;
		}
		
		return element.getTextContent();
	}

	private static Element getChildElement(Element root, String tagName) {
		if(root == null) {
			return null;
		}
		
		NodeList nodeList = root.getElementsByTagName(tagName);
		if(nodeList == null || nodeList.getLength() == 0) {
			return null;
		}
		
		Node node = nodeList.item(0);
		if(node.getNodeType() != Node.ELEMENT_NODE) {
			return null;
		}
		
		return (Element) node;
	}

}
