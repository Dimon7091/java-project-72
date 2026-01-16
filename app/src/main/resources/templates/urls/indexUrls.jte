@import hexlet.code.models.viewModels.UrlsPage
@import hexlet.code.models.viewModels.BasePage

@param UrlsPage page

@template.layout(
page = page,
content = @`
    <section>
        <div class="container-xl mt-5">
            <div class="title">
                <h1>Сайты</h1>
            </div>
            <table class="table table-bordered table-hover">
                <thead class="table-dark">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Имя</th>
                    <th scope="col">Последняя проверка</th>
                    <th scope="col">Код ответа</th>
                </tr>
                </thead>
                <tbody>
                @if(page != null && page.getUrls() != null)
                    @for(var url : page.getUrls())

                        <tr>
                            <th scope="row">${url.getId()}</th>
                            <td><a href="/urls/${url.getId()}">${url.getName()}</a></td>
                            <td>${url.getLastCheck()}</td>
                            <td>${url.getStatusCode()}</td>
                        </tr>

                    @endfor
                @endif
                </tbody>
            </table>
        </div>
    </section>
`
)