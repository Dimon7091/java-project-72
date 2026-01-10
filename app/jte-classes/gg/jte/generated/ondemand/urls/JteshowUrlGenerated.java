package gg.jte.generated.ondemand.urls;
import hexlet.code.models.viewModels.UrlPage;
import hexlet.code.models.viewModels.BasePage;
@SuppressWarnings("unchecked")
public final class JteshowUrlGenerated {
	public static final String JTE_NAME = "urls/showUrl.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,3,5,5,8,8,18,18,18,22,22,22,26,26,26,54,54,54,54,54,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage page) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.JtelayoutGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <section>\n        <div class=\"container-xl mt-5\">\n            <div class=\"title\">\n                <h1>Сайт:</h1>\n            </div>\n            <table class=\"table table-bordered table-hover\">\n                <tbody>\n                    <tr>\n                        <td>ID</td>\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getId());
				jteOutput.writeContent("</td>\n                    </tr>\n                    <tr>\n                        <td>Имя</td>\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getName());
				jteOutput.writeContent("</td>\n                    </tr>\n                    <tr>\n                        <td>Дата создания</td>\n                        <td>");
				jteOutput.setContext("td", null);
				jteOutput.writeUserContent(page.getUrl().getCreatedAt().toString());
				jteOutput.writeContent("</td>\n                    </tr>\n                </tbody>\n            </table>\n        </div>\n\n        <div class=\"container-xl mt-5 \">\n            <div class=\"title\">\n                <h2>Проверки</h2>\n            </div>\n            <a type=\"button\" class=\"btn btn-primary\">Запустить проверку</a>\n            <table class=\"table table-bordered table-hover mt-3\">\n                <thead class=\"table-dark\">\n                <tr>\n                    <th scope=\"col\">ID</th>\n                    <th scope=\"col\">Код ответа</th>\n                    <th scope=\"col\">title</th>\n                    <th scope=\"col\">h1</th>\n                    <th scope=\"col\">description</th>\n                    <th scope=\"col\">Дата проверки</th>\n                </tr>\n                </thead>\n                <tbody>\n\n                </tbody>\n            </table>\n        </div>\n    </section>\n");
			}
		}, page);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage page = (UrlPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
