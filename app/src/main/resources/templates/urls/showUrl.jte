@import hexlet.code.models.viewModels.UrlPage
@import hexlet.code.models.viewModels.BasePage

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
                    <tr>
                        <td>ID</td>
                        <td>${page.getUrl().getId()}</td>
                    </tr>
                    <tr>
                        <td>Имя</td>
                        <td>${page.getUrl().getName()}</td>
                    </tr>
                    <tr>
                        <td>Дата создания</td>
                        <td>${page.getUrl().getCreatedAt().toString()}</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <div class="container-xl mt-5 ">
            <div class="title">
                <h2>Проверки</h2>
            </div>
            <a type="button" class="btn btn-primary">Запустить проверку</a>
            <table class="table table-bordered table-hover mt-3">
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
                <tbody>

                </tbody>
            </table>
        </div>
    </section>
`
)