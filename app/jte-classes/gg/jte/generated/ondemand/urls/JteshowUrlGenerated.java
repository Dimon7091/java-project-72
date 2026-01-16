package gg.jte.generated.ondemand.urls;
import hexlet.code.models.viewModels.UrlPage;
import hexlet.code.models.viewModels.BasePage;
import gg.jte.Content;
@SuppressWarnings("unchecked")
public final class JteshowUrlGenerated {
	public static final String JTE_NAME = "urls/showUrl.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,4,4,4,4,7,7,10,10,14,14,14,18,18,18,19,19,19,20,20,20,29,29,29,29,31,31,31,31,31,31,31,31,31,34,35,35,35,35,35,35,35,35,35,37,49,49,50,50,52,52,52,53,53,53,54,54,54,55,55,55,56,56,56,57,57,57,59,59,60,60,67,67,67,67,67,4,5,5,5,5};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content table, UrlPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.JtelayoutGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <section>\n        <div class=\"container-xl mt-5\">\n            <div class=\"title\">\n                <h1>Сайт: ");
				jteOutput.setContext("h1", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</h1>\n            </div>\n            <table class=\"table table-bordered table-hover\">\n                <tbody>\n                <tr><td>ID</td><td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</td></tr>\n                <tr><td>Имя</td><td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td></tr>\n                <tr><td>Дата создания</td><td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getCreatedAt());
				jteOutput.writeContent("</td></tr>\n                </tbody>\n            </table>\n        </div>\n\n        <div class=\"container-xl mt-5\">\n            <div class=\"title\">\n                <h2>Проверки</h2>\n            </div>\n            <form class=\"check-form\" method=\"post\" action=\"/urls/");
				jteOutput.setContext("form", "action");
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.setContext("form", null);
				jteOutput.writeContent("/checks\">\n                <button type=\"submit\" class=\"btn btn-primary\">Запустить проверку</button>\n                <input type=\"hidden\" name=\"urlName\"");
				var __jte_html_attribute_0 = page.getUrl().getName();
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" value=\"");
					jteOutput.setContext("input", "value");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("input", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(">\n            </form>\n\n            ");
				jteOutput.writeContent("\n            <div id=\"checks-tbody-container\" class=\"mt-3\"");
				var __jte_html_attribute_1 = page.getUrl().getId();
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_1)) {
					jteOutput.writeContent(" data-url-id=\"");
					jteOutput.setContext("div", "data-url-id");
					jteOutput.writeUserContent(__jte_html_attribute_1);
					jteOutput.setContext("div", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(">\n                <table class=\"table table-bordered table-hover\">\n                    ");
				jteOutput.writeContent("\n                    <thead class=\"table-dark\">\n                    <tr>\n                        <th scope=\"col\">ID</th>\n                        <th scope=\"col\">Код ответа</th>\n                        <th scope=\"col\">title</th>\n                        <th scope=\"col\">h1</th>\n                        <th scope=\"col\">description</th>\n                        <th scope=\"col\">Дата проверки</th>\n                    </tr>\n                    </thead>\n                    <tbody id=\"checks-tbody\">\n                    ");
				if (page.getUrlChecks().isPresent()) {
					jteOutput.writeContent("\n                        ");
					for (var check : page.getUrlChecks().get()) {
						jteOutput.writeContent("\n                            <tr>\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getId());
						jteOutput.writeContent("</td>\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getStatusCode());
						jteOutput.writeContent("</td>\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getTitle());
						jteOutput.writeContent("</td>\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getH1());
						jteOutput.writeContent("</td>\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getDescription());
						jteOutput.writeContent("</td>\n                                <td>");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(check.getCreatedAt());
						jteOutput.writeContent("</td>\n                            </tr>\n                        ");
					}
					jteOutput.writeContent("\n                    ");
				}
				jteOutput.writeContent("\n                    </tbody>\n                </table>\n            </div>\n        </div>\n    </section>\n\n");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content table = (Content)params.get("table");
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, table, page);
	}
}
