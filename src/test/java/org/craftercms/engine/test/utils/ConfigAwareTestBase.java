package org.craftercms.engine.test.utils;

import org.apache.commons.configuration2.XMLConfiguration;
import org.craftercms.commons.http.RequestContext;
import org.craftercms.engine.service.context.SiteContext;
import org.craftercms.engine.util.ConfigUtils;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.mockito.Mockito.when;

/**
 * Base for security config aware related tests.
 *
 * @author avasquez
 */
public class ConfigAwareTestBase {

    @Mock
    protected SiteContext siteContext;
    protected XMLConfiguration config;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(siteContext.getSiteName()).thenReturn("test");

        setCurrentRequestContext();
        setCurrentSiteContext();

        config = ConfigUtils.readXmlConfiguration(new ClassPathResource("config/site-config.xml"), ',');

        when(siteContext.getConfig()).thenReturn(config);
    }

    @After
    public void tearDown() throws Exception {
        clearCurrentRequestContext();
    }

    private void setCurrentSiteContext() {
        SiteContext.setCurrent(siteContext);
    }

    private void setCurrentRequestContext() {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/");
        MockHttpServletResponse response = new MockHttpServletResponse();
        RequestContext context = new RequestContext(request, response, null);

        RequestContext.setCurrent(context);
    }

    private void clearCurrentRequestContext() {
        RequestContext.clear();
    }

}
