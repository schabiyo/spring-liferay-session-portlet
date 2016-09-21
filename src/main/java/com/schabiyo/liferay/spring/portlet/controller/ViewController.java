package com.schabiyo.liferay.spring.portlet.controller;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PortalUtil;
import com.schabiyo.liferay.spring.portlet.controller.Keys.Views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("VIEW")
public class ViewController {
    private static final Log LOGGER = LogFactoryUtil.getLog(ViewController.class);

    
    private static String PORT_ENV_VARIABLE_NAME = "PORT";
	private static String INSTANCE_GUID_ENV_VARIABLE_NAME = "INSTANCE_GUID";
	private static final String INSTANCE_INDEX_ENV_VARIABLE_NAME = "INSTANCE_INDEX";


	private AppInfo appInfo;


	@Autowired
	void setEnvironment(Environment env) {
		appInfo = new AppInfo();
		System.out.println("INSTANCE_GUID_ENV_VARIABLE_NAME: " + env.getProperty(INSTANCE_GUID_ENV_VARIABLE_NAME));
		System.out.println("INSTANCE_INDEX_ENV_VARIABLE_NAME: " + env.getProperty(INSTANCE_INDEX_ENV_VARIABLE_NAME));
		System.out.println("PORT_ENV_VARIABLE_NAME: " + env.getProperty(PORT_ENV_VARIABLE_NAME));

		appInfo.setAppGuid(env.getProperty(INSTANCE_GUID_ENV_VARIABLE_NAME));
		appInfo.setAppIndex(env.getProperty(INSTANCE_INDEX_ENV_VARIABLE_NAME));

	}
    
    @RenderMapping
    public String defaultView(RenderRequest request) {

        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Default View");
        }
        List<String> sessionDatas = new ArrayList<String>();
		HttpSession session = getHttSession(request);

		System.out.println(" SESSION ID =" + session.getId());

		Map sessionAttributes = new HashMap();

		Enumeration<?> e = session.getAttributeNames();
		while (e.hasMoreElements()){
			String name = (String) e.nextElement();
			Object value = session.getAttribute(name);		
			System.out.println("SessionViewPController:" + name + "=" + value);
			sessionAttributes.put(name, value);
		}
		request.setAttribute("sessionAttributes", sessionAttributes);
		request.setAttribute("sessionId", session.getId());
		request.setAttribute("instanceId", appInfo.getAppIndex());
		request.setAttribute("instanceGuid", appInfo.getAppGuid());
		try {
			request.setAttribute("instanceIp", InetAddress.getLocalHost().getHostAddress());
			request.setAttribute("instancePort", System.getenv("PORT"));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        return Views.DEFAULT_VIEW;
    }
    
    
    @RequestMapping(method = RequestMethod.POST)
	@ActionMapping(params="action=add-data")
	public void addName(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException{
		HttpSession session = getHttSession(actionRequest);

		String key = actionRequest.getParameter("dataKey"); 
		String value = actionRequest.getParameter("dataValue"); 
		System.out.println("*********************  Setting session data:"+key+":" + value);		
	
	
		session.setAttribute("Sani", "Sani is terrific");
		session.setAttribute(key, value);


	}

    
    private HttpSession getHttSession(PortletRequest request){
		
		HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(request);
		return httpRequest.getSession();
	}

    @RenderMapping(params = "render=alternative-view")
    public String alternativeView() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Alternative view");
        }

        return Views.ALTERNATIVE_VIEW;
    }

    @ActionMapping(params = "action=action-one")
    public void actionOne() {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Action one");
        }

        // Returns control to default view
    }

    @ActionMapping(params = "action=action-two")
    public void actionTwo(ActionResponse actionResponse) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Action two");
        }

        // Returns control to alternative view
        actionResponse.setRenderParameter("render", "alternative-view");
    }

    @RequestMapping(method = RequestMethod.POST)
	@ActionMapping(params="action=kill-instance")
	public void killInstance(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException{
		System.out.println("*********************  KILLIG INSTANCE:" + appInfo.getAppGuid());
		System.exit(1);

	}
  
}
