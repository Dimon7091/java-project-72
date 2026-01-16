@import hexlet.code.models.viewModels.UrlPage
@import hexlet.code.models.viewModels.BasePage
@import gg.jte.Content

@param Content table
@param UrlPage page

@template.layout(
page = page,
content = @`
    <section>
        <div class="container-xl mt-5">
            <div class="title">
                <h1>Сайт: ${page.getUrl().getName()}</h1>
            </div>
            <table class="table table-bordered table-hover">
                <tbody>
                <tr><td>ID</td><td>${page.getUrl().getId()}</td></tr>
                <tr><td>Имя</td><td>${page.getUrl().getName()}</td></tr>
                <tr><td>Дата создания</td><td>${page.getUrl().getCreatedAt()}</td></tr>
                </tbody>
            </table>
        </div>

        <div class="container-xl mt-5">
            <div class="title">
                <h2>Проверки</h2>
            </div>
            <form class="check-form" method="post" action="/urls/${page.getUrl().getId()}/checks">
                <button type="submit" class="btn btn-primary">Запустить проверку</button>
                <input type="hidden" name="name" value="${page.getUrl().getName()}">
            </form>

            <!-- 🔥 Vue ТОЛЬКО для tbody -->
            <div id="checks-tbody-container" class="mt-3" data-url-id="${page.getUrl().getId()}">
                <table class="table table-bordered table-hover">
                    <!-- СТАТИЧНЫЙ ЗАГОЛОВОК - ОСТАЕТСЯ! -->
                    <thead class="table-dark">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Код ответа</th>
                        <th scope="col">title</th>
                        <th scope="col">h1</th>
                        <th scope="col">description</th>
                        <th scope="col">Дата проверки</th>
                    </tr>
                    </thead>
                    <tbody id="checks-tbody">
                    @if(page.getUrlChecks().isPresent())
                        @for(var check : page.getUrlChecks().get())
                            <tr>
                                <td>${check.getId()}</td>
                                <td>${check.getStatusCode()}</td>
                                <td>${check.getTitle()}</td>
                                <td>${check.getH1()}</td>
                                <td>${check.getDescription()}</td>
                                <td>${check.getCreatedAt()}</td>
                            </tr>
                        @endfor
                    @endif
                    </tbody>
                </table>
            </div>
        </div>
    </section>

`
)