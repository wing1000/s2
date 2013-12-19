package fengfei.web.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fengfei.fir.utils.Path;

//@WebFilter(urlPatterns = { "/photo/*" })
public class PhotoFilter implements Filter {

    static Logger logger = LoggerFactory.getLogger(PhotoFilter.class);

    @Override
    public void init(FilterConfig config) throws ServletException {
        logger.info("PhotoFilter inited.");

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException,
        ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        OutputStream out = response.getOutputStream();
        String uri = req.getRequestURI();
        String servletPath = req.getServletPath();

        // logger.debug(String.format(
        // "uri=%s, servletPath=%s,contextPath=%s,getPathInfo=%s",
        // uri,
        // servletPath,
        // req.getContextPath(),
        // req.getPathInfo()));
        int index = servletPath.indexOf('/', 3);
        File file = new File(Path.UPLOAD_PATH + servletPath.substring(index));
        logger.debug(String.format("get file:%s", file.getAbsolutePath()));

        if (!file.exists()) {

            index = servletPath.lastIndexOf("/");
            String path = req.getServletContext().getRealPath(
                "/photo/404/" + servletPath.substring(index));
            file = new File(path);
        }
        FileInputStream in = new FileInputStream(file);
        int len = 0;
        byte[] bs = new byte[8192];
        while ((len = in.read(bs)) > 0) {
            out.write(bs, 0, len);
        }
        in.close();
        out.flush();

        out.close();

    }

    @Override
    public void destroy() {

    }

}
