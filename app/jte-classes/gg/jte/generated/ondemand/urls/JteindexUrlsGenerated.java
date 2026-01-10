package gg.jte.generated.ondemand.urls;
import hexlet.code.models.viewModels.UrlsPage;
import hexlet.code.models.viewModels.BasePage;
@SuppressWarnings("unchecked")
public final class JteindexUrlsGenerated {
	public static final String JTE_NAME = "urls/indexUrls.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,3,5,5,8,8,24,24,25,25,28,28,28,29,29,29,29,29,29,29,34,34,35,35,40,40,40,40,40,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.JtelayoutGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <section>\n        <div class=\"container-xl mt-5\">\n            <div class=\"title\">\n                <h1>Сайты</h1>\n            </div>\n            <table class=\"table table-bordered table-hover\">\n                <thead class=\"table-dark\">\n                <tr>\n                    <th scope=\"col\">ID</th>\n                    <th scope=\"col\">Имя</th>\n                    <th scope=\"col\">Последняя проверка</th>\n                    <th scope=\"col\">Код ответа</th>\n                </tr>\n                </thead>\n                <tbody>\n                ");
				if (page != null && page.getUrls() != null) {
					jteOutput.writeContent("\n                    ");
					for (var url : page.getUrls()) {
						jteOutput.writeContent("\n\n                        <tr>\n                            <th scope=\"row\">");
						jteOutput.setContext("th", null);
						jteOutput.writeUserContent(url.getId());
						jteOutput.writeContent("</th>\n                            <td><a href=\"/urls/");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(url.getId());
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\">");
						jteOutput.setContext("a", null);
						jteOutput.writeUserContent(url.getName());
						jteOutput.writeContent("</a></td>\n                            <td></td>\n                            <td></td>\n                        </tr>\n\n                    ");
					}
					jteOutput.writeContent("\n                ");
				}
				jteOutput.writeContent("\n                </tbody>\n            </table>\n        </div>\n    </section>\n");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage page = (UrlsPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
