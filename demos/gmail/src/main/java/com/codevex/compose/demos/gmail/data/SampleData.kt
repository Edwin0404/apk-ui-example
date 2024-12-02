package com.codevex.compose.demos.gmail.data

var sampleMessage = """
    
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Simple Email</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 0;
                background-color: #f4f4f4;
            }
            table {
                max-width: 600px;
                margin: 20px auto;
                background-color: #ffffff;
                border: 1px solid #dddddd;
                border-spacing: 0;
            }
            td {
                padding: 20px;
                text-align: left;
            }
            h1 {
                color: #333333;
            }
            p {
                color: #666666;
            }
            a {
                color: #1a73e8;
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        <table>
            <tr>
                <td>
                    <h1>Welcome!</h1>
                    <p>Thank you for subscribing to our newsletter. We’re excited to have you on board!</p>
                    <p>Click the button below to confirm your email:</p>
                    <p style="text-align: center;">
                        <a href="https://example.com/confirm" style="background-color: #1a73e8; color: #ffffff; padding: 10px 20px; border-radius: 5px; display: inline-block;">Confirm Email</a>
                    </p>
                    <p>If you didn’t request this email, you can safely ignore it.</p>
                    <p>Best regards,<br />The Team</p>
                </td>
            </tr>
        </table>
    </body>
    </html>

    
""".trimIndent()

