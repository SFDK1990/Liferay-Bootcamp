<%@ include file="../init.jsp"%>

<p>
    <%
        AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute(WebKeys.ASSET_RENDERER);
    %>

    <%= HtmlUtil.escape(assetRenderer.getSummary(renderRequest, renderResponse)) %>
</p>