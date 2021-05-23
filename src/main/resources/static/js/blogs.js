const blogs = {
    init: function () {
        const _this = this;

        let btnSubscriptions = document.getElementsByName('btn-subscription')
        let blogIds = document.getElementsByName('blogId')

        if (btnSubscriptions != null) {
            let index = 0

            for (const btnSubscription of btnSubscriptions) {
                if (btnSubscription.value == null || btnSubscription.value === '') {
                    let blogId = blogIds[index].dataset.id

                    btnSubscription.addEventListener('click', function () {
                        _this.save(blogId)
                    });
                } else {
                    let subscriptionId = btnSubscription.value

                    btnSubscription.addEventListener('click', function () {
                        _this.delete(subscriptionId)
                    });
                }

                index++
            }
        }
    },
    save: function (pBlogId) {
        if (confirm('구독 하시겠습니까?')) {
            $.ajax({
                type: 'POST',
                url: '/api/blog/subscription/' + pBlogId,
            }).done(function () {
                alert('구독이 완료 되었습니다.')
                window.location.href = '/blogs'
            }).fail(function (error) {
                alert(JSON.stringify(error))
            })
        }
    },
    delete: function (pSubscriptionId) {
        if (confirm('구독 취소 하시겠습니까?')) {
            $.ajax({
                type: 'DELETE',
                url: 'api/blog/subscription/' + pSubscriptionId,
            }).done(function () {
                alert('구독 취소가 되었습니다.')
                window.location.href = '/blogs'
            }).fail(function (error) {
                alert(JSON.stringify(error))
            })
        }
    }
};

blogs.init()